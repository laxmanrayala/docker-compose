package com.projectla.deliveryapp.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectla.deliveryapp.Dto.OrderResponse;
import com.projectla.deliveryapp.Enum.OrderStatus;
import com.projectla.deliveryapp.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // 🔹 CHECKOUT
    @PostMapping("/checkout")
    public OrderResponse placeOrder(@PathVariable Long userId) {
        return orderService.placeOrder(userId);
    }

    // 🔹 GET ALL ORDERS
    @GetMapping
    public List<OrderResponse> getOrders(@PathVariable Long userId) {
        return orderService.getOrders(userId);
    }

    // 🔹 GET SINGLE ORDER
    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long userId,
                                      @PathVariable Long orderId) {
        return orderService.getOrderById(userId, orderId);
    }

    // 🔹 UPDATE STATUS
    @PatchMapping("/{orderId}/status")
    public OrderResponse updateStatus(@PathVariable Long userId,
                                      @PathVariable Long orderId,
                                      @RequestParam OrderStatus status) {
        return orderService.updateOrderStatus(userId, orderId, status);
    }
}
