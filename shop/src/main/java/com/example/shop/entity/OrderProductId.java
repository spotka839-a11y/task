package com.example.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductId implements Serializable {

    @Column(name = "product_id")
    private String productId;

    @Column(name = "order_id")
    private Integer orderId;

    public OrderProductId(String id, Object o) {
    }
}