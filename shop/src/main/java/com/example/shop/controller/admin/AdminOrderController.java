package com.example.shop.controller;

import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.StatusRepository;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders",
                orderRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate")));
        return "admin/orders";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable Integer id, Model model) {
        model.addAttribute("order", orderService.findById(id));
        model.addAttribute("statuses", statusRepository.findAll());
        return "admin/order-details";
    }

    // Задание 2
    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Integer id,
                               @RequestParam Integer statusId,
                               RedirectAttributes ra) {
        orderService.changeStatus(id, statusId);
        ra.addFlashAttribute("message", "Статус заказа обновлён");
        return "redirect:/admin/orders/" + id;
    }
}