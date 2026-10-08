package com.example.shop.controller.api;

import com.example.shop.entity.Order;
import com.example.shop.entity.Status;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderApiController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/my")
    public List<Map<String, Object>> myOrders() {
        return orderService.findMyOrders().stream()
                .map(this::toMap)
                .collect(Collectors.toList());
    }

    private Map<String, Object> toMap(Order o, Status s) {
        return Map.of(
                "id", o.getId(),
                "status", s.getTitle(),
                "createDate", o.getCreateDate().toString(),
                "deliveryDate", o.getDeliveryDate().toString(),
                "pickupPoint", o.getPickupPoint().getAddress(),
                "getCode", o.getGetCode()
        );
    }
}