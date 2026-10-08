package com.example.shop.repository;

import com.example.shop.entity.PickupPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupPointRepository extends JpaRepository<PickupPoint, Integer> {
}