package com.example.shop.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DateUtils() {
    }

    /**
     * LocalDate -> "дд.мм.гггг". Для null возвращает пустую строку.
     */
    public static String format(LocalDate date) {
        return date == null ? "" : date.format(FORMATTER);
    }

    /**
     * "дд.мм.гггг" -> LocalDate. Для пустой строки возвращает null.
     */
    public static LocalDate parse(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }
        try {
            return LocalDate.parse(text.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Ожидается формат дд.мм.гггг, получено: " + text, e);
        }
    }
}
