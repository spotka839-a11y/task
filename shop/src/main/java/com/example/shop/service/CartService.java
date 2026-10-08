package com.example.shop.service;

import com.example.shop.dto.Cart;
import com.example.shop.dto.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.util.CartUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private static final String CART_KEY = "cart";

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpSession session, Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Количество должно быть больше нуля");
        }
        Cart cart = getCart(session);

        // сколько будет в корзине после добавления
        int total = CartUtils.quantityOf(cart, product.getId()) + quantity;
        checkStock(product, total);

        cart.addItem(new CartItem(
                product.getId(),
                product.getTitle(),
                product.getCost(),
                quantity,
                product.getPhoto()
        ));
    }

    public void updateQuantity(HttpSession session, Product product, int quantity) {
        if (quantity > 0) {
            checkStock(product, quantity);
        }
        getCart(session).updateQuantity(product.getId(), quantity);
    }

    public void removeFromCart(HttpSession session, String productId) {
        getCart(session).removeItem(productId);
    }

    public void clearCart(HttpSession session) {
        getCart(session).clear();
    }

    private void checkStock(Product product, int requested) {
        Integer stock = product.getQuantityInStock();
        int available = stock == null ? 0 : stock;
        if (requested > available) {
            throw new IllegalArgumentException(
                    "Недостаточно товара «" + product.getTitle() + "» на складе. Доступно: " + available + " шт.");
        }
    }
}