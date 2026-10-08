package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryForm {
    private Integer id;

    @NotBlank(message = "Название обязательно")
    @Size(min = 2, max = 200, message = "Название от 2 до 200 символов")
    private String title;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}