package com.example.shop.controller;

import com.example.shop.dto.Cart;
import com.example.shop.entity.Product;
import com.example.shop.service.CartService;
import com.example.shop.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "cart";
    }



    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String productId, HttpSession session) {
        cartService.removeFromCart(session, productId);
        return "redirect:/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String productId,
                            @RequestParam(defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes ra) {
        Product product = productService.findById(productId);
        try {
            cartService.addToCart(session, product, quantity);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/product/" + productId;
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam String productId,
                                 @RequestParam int quantity,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        try {
            Product product = productService.findById(productId);
            cartService.updateQuantity(session, product, quantity);
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        cartService.clearCart(session);
        return "redirect:/cart";
    }
}