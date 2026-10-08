package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pickup_point")
@Data
public class PickupPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}