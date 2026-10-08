package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.service.UserService;
import com.example.shop.util.DateUtils;
import com.example.shop.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null) {
            return "redirect:/login";
        }
        User user = userService.findByUsername(username).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("registeredAt", DateUtils.format(user.getRegistrationDate()));
        return "profile";
    }
}
