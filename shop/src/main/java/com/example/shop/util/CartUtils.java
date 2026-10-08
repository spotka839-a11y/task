package com.example.shop.util;

import com.example.shop.dto.Cart;
import com.example.shop.dto.CartItem;

import java.math.BigDecimal;

public final class CartUtils {

    private CartUtils() {
    }

    /** Итоговая сумма корзины. */
    public static BigDecimal sumTotal(Cart cart) {
        return cart.getItems().stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Общее количество единиц товара в корзине (то, что показывается в шапке). */
    public static int countItems(Cart cart) {
        return cart.getItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    /** Есть ли товар в корзине. */
    public static boolean hasProduct(Cart cart, String productId) {
        return cart.getItems().stream()
                .anyMatch(i -> i.getProductId().equals(productId));
    }

    /** Сколько единиц товара уже лежит в корзине (0, если нет). */
    public static int quantityOf(Cart cart, String productId) {
        return cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId))
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}