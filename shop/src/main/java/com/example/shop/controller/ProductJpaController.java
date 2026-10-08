package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductJpaController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products-jpa")
    public List<Product> products() {
        return productRepository.findAll();
    }
}