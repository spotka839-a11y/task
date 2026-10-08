package com.example.shop.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class PriceUtils {

    private PriceUtils() {
    }

    /**
     * Вычисляет итоговую цену товара с учётом скидки.
     */
    public static BigDecimal applyDiscount(BigDecimal basePrice, Integer discountAmount) {
        if (basePrice == null) {
            return BigDecimal.ZERO;
        }
        if (discountAmount == null || discountAmount <= 0) {
            return basePrice.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal factor = BigDecimal.ONE.subtract(
                BigDecimal.valueOf(discountAmount).divide(BigDecimal.valueOf(100))
        );
        return basePrice.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Форматирует цену в вид "1 234,56 ₽".
     */
    public static String format(BigDecimal price) {
        if (price == null) {
            return "—";
        }
        return String.format("%,.2f ₽", price);
    }
}