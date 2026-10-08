package com.example.shop.controller.admin;

import com.example.shop.dto.CategoryForm;
import com.example.shop.entity.Category;
import com.example.shop.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Category category = categoryService.findById(id);
        CategoryForm form = new CategoryForm();
        form.setId(category.getId());
        form.setTitle(category.getTitle());
        model.addAttribute("categoryForm", form);
        return "admin/category-form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("categoryForm") CategoryForm form,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "admin/category-form";
        }
        categoryService.updateFromForm(id, form);
        return "redirect:/categories-page";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/categories-page";
    }
}