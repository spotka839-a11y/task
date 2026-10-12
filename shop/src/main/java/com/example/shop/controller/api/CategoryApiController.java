package com.example.shop.controller.api;

import com.example.shop.dto.CategoryDto;
import com.example.shop.entity.Category;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.util.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryApiController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<CategoryDto> list() {
        return categoryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return categoryRepository.findById(id)
                .<ResponseEntity<?>>map(c -> ResponseEntity.ok(toDto(c)))
                .orElseGet(() -> ResponseEntity.status(404)
                        .body(ApiUtils.error("Категория не найдена: " + id)));
    }

    private CategoryDto toDto(Category c) {
        CategoryDto dto = new CategoryDto();
        dto.setId(c.getId());
        dto.setTitle(c.getTitle());
        return dto;
    }
}