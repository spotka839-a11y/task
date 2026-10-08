package com.example.shop.controller;

import com.example.shop.entity.Product;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private CategoryService categoryService;
    private ProductService productService;

    @GetMapping("/products")
    public List<Map<String, Object>> products() {
        return jdbcTemplate.queryForList(
                "SELECT id, title, cost, quantity_in_stock FROM product LIMIT 10"
        );
    }
    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable String id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-details";
    }

//    @GetMapping("/product/{id}/photo")
//    public ResponseEntity<byte[]> productPhoto(@PathVariable String id) {
//        Product product = productService.findById(id);
//        byte[] photo = product.getPhoto();
//
//        if (photo == null || photo.length == 0) {
//            // Заглушка: 1x1 прозрачный PNG
//            byte[] placeholder = new byte[]{
//                    (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
//                    // (сокращённый массив — для простоты лучше сгенерировать картинку-заглушку)
//            };
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_PNG)
//                    .body(placeholder);
//        }
//
//        // Определить тип по первым байтам (JPEG или PNG)
//        MediaType mediaType = detectImageType(photo);
//
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
//                .body(photo);
//    }

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping("/product/{id}/photo")
    public ResponseEntity<byte[]> productPhoto(@PathVariable String id) throws IOException {
        Product product = productService.findById(id);
        byte[] photo = product.getPhoto();

        if (photo == null || photo.length == 0) {
            Resource placeholder = resourceLoader.getResource("classpath:static/images/placeholder.png");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(placeholder.getInputStream().readAllBytes());
        }

        MediaType mediaType = detectImageType(photo);
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
                .body(photo);
    }

    private MediaType detectImageType(byte[] data) {
        if (data.length >= 3
                && (data[0] & 0xFF) == 0xFF
                && (data[1] & 0xFF) == 0xD8
                && (data[2] & 0xFF) == 0xFF) {
            return MediaType.IMAGE_JPEG;
        }
        return MediaType.IMAGE_PNG;
    }
    @GetMapping("/catalog")
    public String catalog(@RequestParam(required = false) Integer categoryId,
                          @RequestParam(required = false) String search,
                          @RequestParam(required = false) String sort,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findPage(categoryId, search, sort, page, 12));
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        return "catalog";
    }
}