package com.example.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;

    public PageDto(List<T> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}