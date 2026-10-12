/*package com.example.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice(basePackages = "com.example.shop.controller")
public class MvcExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(IllegalArgumentException e, Model model) {
        log.warn("Не найдено: {}", e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleForbidden(SecurityException e, Model model) {
        log.warn("Доступ запрещён: {}", e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "error/403";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAll(Exception e, Model model) {
        log.error("Внутренняя ошибка", e);
        model.addAttribute("message", "Что-то пошло не так. Мы уже разбираемся.");
        return "error/500";
    }
}*/