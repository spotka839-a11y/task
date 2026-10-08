package com.example.shop.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("Пароль 'demo': " + encoder.encode("demo"));
        System.out.println("Пароль 'admin': " + encoder.encode("admin"));
    }
}