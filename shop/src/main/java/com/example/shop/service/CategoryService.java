package com.example.shop.service;

import com.example.shop.entity.Category;
import com.example.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.shop.dto.CategoryForm;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Если у вас уже есть CategoryService — просто добавьте в него недостающие методы.
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
        categoryRepository.flush(); // чтобы ошибка внешнего ключа возникла здесь, а не при коммите
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Категория не найдена: " + id));
    }

    @Transactional
    public void updateFromForm(Integer id, CategoryForm form) {
        Category category = findById(id);
        category.setTitle(form.getTitle());
        categoryRepository.save(category);
    }
}
