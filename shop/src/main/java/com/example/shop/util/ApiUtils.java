package com.example.shop.util;

import java.util.Map;

public class ApiUtils {

    private ApiUtils() {}

    public static Map<String, String> error(String message) {
        return Map.of("error", message);
    }

    public static Map<String, String> success(String message) {
        return Map.of("success", message);
    }
}