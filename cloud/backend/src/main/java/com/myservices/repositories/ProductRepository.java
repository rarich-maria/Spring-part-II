package com.myservices.repositories;

import com.myservices.ProductDTO;
import com.myservices.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<ProductDTO> findAllBy();
}