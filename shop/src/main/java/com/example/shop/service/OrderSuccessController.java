package com.example.shop.service;

import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderSuccessController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order-success")
    public String orderSuccess(@RequestParam Integer orderId, Model model) {
        model.addAttribute("order", orderService.findMyOrder(orderId));
        return "order-success";
    }
}