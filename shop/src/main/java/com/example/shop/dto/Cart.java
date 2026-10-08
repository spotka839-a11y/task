package com.example.shop.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Cart implements Serializable {

    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem newItem) {
        Optional<CartItem> existing = items.stream()
                .filter(i -> i.getProductId().equals(newItem.getProductId()))
                .findFirst();

        if (existing.isPresent()) {
            existing.get().setQuantity(existing.get().getQuantity() + newItem.getQuantity());
        } else {
            items.add(newItem);
        }
    }

    public void removeItem(String productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
    }

    public void updateQuantity(String productId, int quantity) {
        if (quantity <= 0) {
            removeItem(productId);
            return;
        }
        items.stream()
                .filter(i -> i.getProductId().equals(productId))
                .findFirst()
                .ifPresent(i -> i.setQuantity(quantity));
    }

    public void clear() {
        items.clear();
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(CartItem::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getItemCount() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }
}