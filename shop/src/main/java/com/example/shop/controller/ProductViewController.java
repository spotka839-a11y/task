package com.example.shop.controller;

import com.example.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductViewController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products-jpa-page")
    public String productsJpaPage(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "products-jpa";
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/products-page")
    public String productsPage(Model model) {
        model.addAttribute("products", jdbcTemplate.queryForList(
                "SELECT id, title, cost, quantity_in_stock FROM product LIMIT 20"
        ));
        return "products";
    }
}