package com.example.shop.controller.admin;

import com.example.shop.entity.Order;
import com.example.shop.entity.Status;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @GetMapping
    public String list(@RequestParam(required = false) Integer statusId,
                       Model model) {
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("selectedStatusId", statusId);

        if (statusId != null) {
            model.addAttribute("orders",
                    orderRepository.findByStatusIdOrderByCreateDateDesc(statusId));
        } else {
            model.addAttribute("orders",
                    orderRepository.findAllByOrderByCreateDateDesc());
        }
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));
        model.addAttribute("order", order);
        model.addAttribute("statuses", statusRepository.findAll());
        return "admin/order-details";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Integer id,
                               @RequestParam Integer statusId) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));
        Status newStatus = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Статус не найден"));
        order.setStatus(newStatus);
        orderRepository.save(order);
        return "redirect:/admin/orders/" + id;
    }
    @GetMapping
    public String list(@RequestParam(required = false) Integer statusId,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                       Model model) {
        // ... логика комбинирования фильтров
    }
}