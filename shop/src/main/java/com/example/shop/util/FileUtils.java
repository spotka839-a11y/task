package com.example.shop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public final class FileUtils {

    private FileUtils() {
    }

    /**
     * Читает содержимое загруженного файла в массив байтов.
     * Возвращает null, если файл не был загружен.
     */
    public static byte[] readBytes(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null;
        }
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл: " + file.getOriginalFilename(), e);
        }
    }

    /**
     * Проверяет, что файл является изображением.
     */
    public static boolean isImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return false;
        }
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}