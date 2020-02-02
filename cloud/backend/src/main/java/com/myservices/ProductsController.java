package com.myservices;
import com.myservices.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductsController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public List<ProductDTO> getProducts() {
        return productService.findAll();
    }

    @PostMapping("/create")
    public void saveProduct(@RequestBody ProductDTOImpl product) {
        productService.save(product);
    }

    @GetMapping("/delete/{id}")
    public void deleteProduct (@PathVariable Long id) {
        productService.delete(id);
    }
}
