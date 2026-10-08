package com.example.shop.service;

import com.example.shop.dto.Cart;
import com.example.shop.dto.CartItem;
import com.example.shop.entity.*;
import com.example.shop.repository.*;
import com.example.shop.util.OrderUtils;
import com.example.shop.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private PickupPointRepository pickupPointRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public Order createOrder(Cart cart, Integer pickupPointId) {
        String username = SecurityUtils.getCurrentUsername();
        if (username == null) {
            throw new SecurityException("Необходимо войти в систему");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        PickupPoint pickupPoint = pickupPointRepository.findById(pickupPointId)
                .orElseThrow(() -> new IllegalArgumentException("Пункт выдачи не найден"));

        Status newStatus = statusRepository.findByTitle("Новый ")
                .orElseThrow(() -> new IllegalStateException("Статус 'Новый' не найден в БД"));

        Order order = new Order();
        order.setUser(user);
        order.setPickupPoint(pickupPoint);
        order.setStatus(newStatus);
        order.setCreateDate(LocalDate.now());
        order.setDeliveryDate(LocalDate.now().plusDays(7));
        order.setGetCode(OrderUtils.generatePickupCode());

        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

            OrderProduct op = new OrderProduct();
            op.setId(new OrderProductId(product.getId(), null)); // id заказа будет после save
            op.setProduct(product);
            op.setOrder(order);
            op.setCount(cartItem.getQuantity());

            order.getItems().add(op);
        }

        return orderRepository.save(order);
    }

    public Order findMyOrder(Integer id) {
        Order order = findById(id);
        String current = SecurityUtils.getCurrentUsername();
        if (order.getUser() == null || !order.getUser().getUsername().equals(current)) {
            throw new SecurityException("Нет доступа к чужому заказу");
        }
        return order;
    }
    public Order findById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ не найден"));
    }

    @Transactional
    public void changeStatus(Integer orderId, Integer statusId) {
        Order order = findById(orderId);
        Status status = statusRepository.findById(statusId)
                .orElseThrow(() -> new IllegalArgumentException("Статус не найден"));
        order.setStatus(status);
        orderRepository.save(order);
    }

}