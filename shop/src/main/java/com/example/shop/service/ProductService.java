package com.example.shop.service;

import com.example.shop.dto.ProductForm;
import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.util.FileUtils;
import com.example.shop.util.SecurityUtils;
import com.example.shop.util.StringUtils;
import com.example.shop.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден: " + id));
    }

    @Transactional
    public Product createFromForm(ProductForm form) {
        Product product = new Product();
        product.setId(form.getId());
        product.setTitle(ValidationUtils.normalize(form.getTitle()));
        product.setCost(form.getCost());
        product.setQuantityInStock(form.getQuantityInStock());
        product.setDescription(ValidationUtils.normalize(form.getDescription()));
        product.setDescription(StringUtils.truncate(ValidationUtils.normalize(form.getDescription()), 100));

        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
        product.setCategory(category);

        // Используем утилиту для чтения файла
        product.setPhoto(FileUtils.readBytes(form.getPhotoFile()));


        return product;
    }


    @Transactional
    public Product updateFromForm(String id, ProductForm form) {
        Product product = findById(id);

        product.setTitle(ValidationUtils.normalize(form.getTitle()));
        product.setCost(form.getCost());
        product.setQuantityInStock(form.getQuantityInStock());
        product.setDescription(ValidationUtils.normalize(form.getDescription()));

        Category category = categoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена"));
        product.setCategory(category);

        // Фото обновляем только если загружено новое
        byte[] newPhoto = FileUtils.readBytes(form.getPhotoFile());
        if (newPhoto != null) {
            product.setPhoto(newPhoto);
        }

        return productRepository.save(product);
    }

    @Transactional
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
    public List<Product> findPopular(int limit) {
        return productRepository.findAll(PageRequest.of(0, limit)).getContent();
    }
    public Page<Product> findPage(Integer categoryId, String search, String sort, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, resolveSort(sort));
        boolean hasSearch = search != null && !search.isBlank();

        if (categoryId == null && !hasSearch) {
            return productRepository.findAll(pageRequest);
        }
        if (categoryId == null) {
            return productRepository.findByTitleContainingIgnoreCase(search.trim(), pageRequest);
        }
        if (!hasSearch) {
            return productRepository.findByCategoryId(categoryId, pageRequest);
        }
        return productRepository.findByCategoryIdAndTitleContainingIgnoreCase(categoryId, search.trim(), pageRequest);
    }

    private Sort resolveSort(String sort) {
        if (sort == null) {
            return Sort.by("title").ascending();
        }
        return switch (sort) {
            case "price_asc"  -> Sort.by("cost").ascending();
            case "price_desc" -> Sort.by("cost").descending();
            default           -> Sort.by("title").ascending();
        };
    }
    public List<Product> searchByTitle(String query) {
        return productRepository.findByTitleContainingIgnoreCase(query);
    }
}