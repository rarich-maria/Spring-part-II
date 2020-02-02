package com.myservices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
public class MainController {
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String showIndex(Model model) {
        ResponseEntity restExchange = restTemplate.exchange("http://localhost:8189/", HttpMethod.GET, null, List.class);
        List<ProductDTO> dtos = (List<ProductDTO>)restExchange.getBody();
        ProductDTOImpl productDTO = new ProductDTOImpl();
        model.addAttribute("product", productDTO);
        model.addAttribute("productsList", dtos);
        return "index";
    }

    @PostMapping("/create")
    public String saveProduct(@ModelAttribute(name = "product") ProductDTOImpl product) {
        System.out.println(product);
        restTemplate.postForEntity("http://localhost:8189/create", product, ProductDTOImpl.class);
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        restTemplate.getForObject("http://localhost:8189/delete/" + id, String.class);
        return "redirect:/";
    }
}
