package com.example.shop.util;

import java.security.SecureRandom;

public final class OrderUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private OrderUtils() {
    }

    /**
     * Генерирует 3-значный код выдачи заказа.
     * От 100 до 999 — так исключаем ведущие нули.
     */
    public static int generatePickupCode() {
        return 100 + RANDOM.nextInt(900);
    }
}