package com.example.shop.util;

public final class StringUtils {

    private StringUtils() {
    }

    /** Обрезает строку до maxLength символов, добавляя «…». */
    public static String truncate(String s, int maxLength) {
        if (s == null || s.length() <= maxLength) {
            return s;
        }
        return s.substring(0, maxLength) + "…";
    }

    /** Первая буква заглавная. */
    public static String capitalize(String s) {
        if (isEmpty(s)) {
            return s;
        }
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    /** null или пустая строка. */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}