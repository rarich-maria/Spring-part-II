package com.geekbrains.decembermarket.controllers;

import com.geekbrains.decembermarket.entites.*;
import com.geekbrains.decembermarket.services.*;
import com.geekbrains.decembermarket.utils.Cart;
import com.geekbrains.decembermarket.utils.ProductFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class MarketController {
    private ProductService productService;
    private CategoryService categoryService;
    private UserService userService;
    private OrderService orderService;
    private AddressService addressService;
    private Cart cart;

    public MarketController(ProductService productService, CategoryService categoryService, UserService userService, OrderService orderService, AddressService addressService, Cart cart) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.orderService = orderService;
        this.addressService = addressService;
        this.cart = cart;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login_page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/";
        }
        User user = userService.findByPhone(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/")
    public String index(Model model, @RequestParam Map<String, String> params) {
        int pageIndex = 0;
        if (params.containsKey("p")) {
            pageIndex = Integer.parseInt(params.get("p")) - 1;
        }
        Pageable pageRequest = PageRequest.of(pageIndex, 10);
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> page = productService.findAll(productFilter.getSpec(), pageRequest);

        List<Category> categories = categoryService.getAll();
        model.addAttribute("filtersDef", productFilter.getFilterDefinition());
        model.addAttribute("categories", categories);
        model.addAttribute("page", page);
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String editProductForm(Model model, @PathVariable Long id) {
        Product product = productService.findById(id);
        List<Category> categories = categoryService.getAll();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "edit_product";
    }

    @PostMapping("/edit")
    public String saveProduct(@ModelAttribute(name = "product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    @GetMapping("/cart/add/{id}")
    public void addProductToCart(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        cart.add(productService.findById(id));
        response.sendRedirect(request.getHeader("referer"));
    }

    @GetMapping("/cart")
    public String showCart(Model model) {
        model.addAttribute("cart", cart);
        return "cart_page";
    }

    @GetMapping("/orders/create")
    public String createOrder(Model model, Principal principal) {
        model.addAttribute("cart", cart);
        User user = userService.findByPhone(principal.getName());
        if (user.getAddress()!=null) {
            model.addAttribute("address", user.getAddress());
        }else {
            Address address = new Address();
            address.setPhone(user.getPhone());
            model.addAttribute("address", address);
        }
        return "order";
    }

    @PostMapping("/orders/save")
    public String saveOrder (@ModelAttribute(name = "address") Address address, Principal principal) {
        addressService.save(address);
        User user = userService.findByPhone(principal.getName());
        if (user.getAddress()==null) {
            user.setAddress(address);
        }
        Order order = new Order(user, cart);
        orderService.save(order);
        return "redirect:/";
    }
}