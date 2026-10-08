package com.example.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "unittype")
@Data
public class UnitType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;
}
