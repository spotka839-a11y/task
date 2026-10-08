package com.example.shop.util;

import java.util.regex.Pattern;

public final class ValidationUtils {

    private static final Pattern ARTICLE_PATTERN = Pattern.compile("^[A-Z0-9]{3,10}$");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private ValidationUtils() {
    }

    public static boolean isValidArticle(String article) {
        return article != null && ARTICLE_PATTERN.matcher(article).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Убирает пробелы в начале/конце, а также двойные пробелы внутри.
     */
    public static String normalize(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }
}