package com.myservices.services;

import com.myservices.ProductDTO;
import com.myservices.ProductDTOImpl;
import com.myservices.entites.Product;
import com.myservices.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAllBy();
    }

    public void save(ProductDTOImpl product) {
        Product p = new Product();
        p.setTitle(product.getTitle());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        productRepository.save(p);
    }

    public void delete (Long id) {productRepository.deleteById(id);}
}
