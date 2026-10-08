package com.example.shop.repository;

import com.example.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByUserUsernameOrderByCreateDateDesc(String username);
    List<Order> findTop5ByOrderByCreateDateDesc();
    List<Order> findAllByOrderByCreateDateDesc();

    List<Order> findByStatusIdOrderByCreateDateDesc(Integer statusId);
    List<Order> findByCreateDateBetweenOrderByCreateDateDesc(LocalDate from, LocalDate to);
}