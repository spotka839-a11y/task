package com.example.shop.controller;

import com.example.shop.entity.Order;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import com.example.shop.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public String myOrders(Model model) {
        model.addAttribute("orders", orderService.findMyOrders());
        return "orders";
    }

    @GetMapping("/{id}")
    public String orderDetails(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));

        // Проверяем, что заказ принадлежит текущему пользователю
        String current = SecurityUtils.getCurrentUsername();
        if (order.getUser() != null && !order.getUser().getUsername().equals(current)) {
            throw new SecurityException("Нет доступа к чужому заказу");
        }

        model.addAttribute("order", order);
        return "order-details";
    }
}