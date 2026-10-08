package com.example.shop.controller.admin;


import com.example.shop.dto.ProductForm;
import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class ProductAdminController {

    // Показать форму редактирования
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Product product = productService.findById(id);

        ProductForm form = new ProductForm();
        form.setId(product.getId());
        form.setTitle(product.getTitle());
        form.setCost(product.getCost());
        form.setQuantityInStock(product.getQuantityInStock());
        form.setDescription(product.getDescription());
        form.setCategoryId(product.getCategory().getId());

        model.addAttribute("productForm", form);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("editMode", true);
        return "admin/product-form";
    }

    // Обработать редактирование
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable String id,
                                @Valid @ModelAttribute("productForm") ProductForm form,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("editMode", true);
            return "admin/product-form";
        }

        productService.updateFromForm(id, form);
        return "redirect:/products-jpa-page";
    }

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    // Показать форму добавления
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/product-form";
    }

    // Обработать отправку формы
    @PostMapping("/new")
    public String saveProduct(@Valid @ModelAttribute("productForm") ProductForm form,
                              BindingResult result,
                              Model model) {
        // Если есть ошибки валидации — вернуть форму с ошибками
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product-form";
        }

        productService.createFromForm(form);
        return "redirect:/products-jpa-page";
    }
    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteById(id);
        return "redirect:/products-jpa-page";
    }
}
