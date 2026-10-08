package com.example.shop.controller;

import com.example.shop.dto.RegistrationForm;
import com.example.shop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("form", new RegistrationForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") RegistrationForm form,
                           BindingResult bindingResult) {
        if (!bindingResult.hasErrors() && userService.existsByUsername(form.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", "Такой логин уже занят");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.register(form);
        return "redirect:/login?registered";
    }
}
