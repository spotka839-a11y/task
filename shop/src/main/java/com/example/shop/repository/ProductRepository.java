package com.example.shop.repository;

import com.example.shop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);

    Page<Product> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Product> findByCategoryIdAndTitleContainingIgnoreCase(Integer categoryId, String title, Pageable pageable);

    List<Product> findByTitleContainingIgnoreCase(String title);
}
