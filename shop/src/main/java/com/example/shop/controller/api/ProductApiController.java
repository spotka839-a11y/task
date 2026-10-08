package com.example.shop.controller.api;

import com.example.shop.dto.ProductDto;
import com.example.shop.entity.Product;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDto> list() {
        return productService.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(toDto(product));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<ProductDto> search(@RequestParam String q) {
        return productService.searchByTitle(q).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ProductDto toDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setTitle(product.getTitle());
        dto.setCost(product.getCost());
        dto.setQuantityInStock(product.getQuantityInStock());
        dto.setDescription(product.getDescription());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryTitle(product.getCategory().getTitle());
        }
        return dto;
    }
}