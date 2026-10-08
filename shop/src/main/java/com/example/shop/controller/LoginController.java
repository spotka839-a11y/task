package com.example.shop.controller;

import com.example.shop.service.UserService;
import com.example.shop.util.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    @GetMapping("/my-profile")
    public String myProfile(Model model) {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userService.findByUsername(username).orElseThrow());
        return "profile";
    }
}