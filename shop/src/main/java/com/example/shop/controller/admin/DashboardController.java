package com.example.shop.controller.admin;

import com.example.shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin")
    public String dashboard(Model model) {
        model.addAttribute("productCount", productRepository.count());
        model.addAttribute("orderCount", orderRepository.count());
        model.addAttribute("userCount", userRepository.count());
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("latestOrders", orderRepository.findTop5ByOrderByCreateDateDesc());
        return "admin/dashboard";
    }
}