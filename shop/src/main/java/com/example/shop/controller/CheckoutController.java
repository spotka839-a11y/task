package com.example.shop.controller;

import com.example.shop.dto.Cart;
import com.example.shop.entity.Order;
import com.example.shop.repository.PickupPointRepository;
import com.example.shop.service.CartService;
import com.example.shop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    @GetMapping
    public String checkoutPage(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("pickupPoints", pickupPointRepository.findAll());
        return "checkout";
    }

    @PostMapping
    public String processCheckout(@RequestParam Integer pickupPointId,
                                  HttpSession session) {
        Cart cart = cartService.getCart(session);
        if (cart.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.createOrder(cart, pickupPointId);

        // очищаем корзину после успешного заказа
        cartService.clearCart(session);

        return "redirect:/order-success?orderId=" + order.getId();
    }
}