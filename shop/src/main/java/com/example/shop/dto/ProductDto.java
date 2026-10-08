package com.example.shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private String id;
    private String title;
    private BigDecimal cost;
    private Integer quantityInStock;
    private String description;
    private Integer categoryId;
    private String categoryTitle;
}