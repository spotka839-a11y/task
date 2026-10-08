package com.example.shop.controller;

import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findPopular(8));
        return "home";
    }
}