package com.example.shop.controller.admin;

import com.example.shop.entity.Order;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.StatusRepository;
import com.example.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String list(@RequestParam(required = false) Integer statusId,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                       @RequestParam(required = false)
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                       Model model) {
        List<Order> orders = orderRepository
                .findAll(Sort.by(Sort.Direction.DESC, "createDate")).stream()
                .filter(o -> statusId == null
                        || (o.getStatus() != null && statusId.equals(o.getStatus().getId())))
                .filter(o -> from == null
                        || (o.getCreateDate() != null && !o.getCreateDate().isBefore(from)))
                .filter(o -> to == null
                        || (o.getCreateDate() != null && !o.getCreateDate().isAfter(to)))
                .collect(Collectors.toList());

        model.addAttribute("orders", orders);
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("selectedStatusId", statusId);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
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