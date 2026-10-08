package com.example.shop.controller.admin;

import com.example.shop.dto.ManufacturerForm;
import com.example.shop.service.ManufacturerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/manufacturers")
public class ManufacturerAdminController {

    @Autowired
    private ManufacturerService manufacturerService;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("manufacturerForm", new ManufacturerForm());
        return "admin/manufacturer-form";
    }

    @PostMapping("/new")
    public String save(@Valid @ModelAttribute("manufacturerForm") ManufacturerForm form,
                       BindingResult result) {
        if (result.hasErrors()) {
            return "admin/manufacturer-form";
        }
        manufacturerService.createFromForm(form);
        return "redirect:/manufacturers-page";
    }
}