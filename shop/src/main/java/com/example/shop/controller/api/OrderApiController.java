package com.example.shop.controller.api;

import com.example.shop.entity.Order;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
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

    private Map<String, Object> toMap(Order o) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", o.getId());
        m.put("status", o.getStatus() != null ? o.getStatus().getTitle() : null);
        m.put("createDate", o.getCreateDate() != null ? o.getCreateDate().toString() : null);
        m.put("deliveryDate", o.getDeliveryDate() != null ? o.getDeliveryDate().toString() : null);
        m.put("pickupPoint", o.getPickupPoint() != null ? o.getPickupPoint().getAddress() : null);
        m.put("getCode", o.getGetCode());
        return m;
    }
}