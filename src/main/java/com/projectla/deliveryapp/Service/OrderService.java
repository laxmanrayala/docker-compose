package com.projectla.deliveryapp.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.projectla.deliveryapp.Dto.OrderItemResponse;
import com.projectla.deliveryapp.Dto.OrderResponse;
import com.projectla.deliveryapp.Entity.Cart;
import com.projectla.deliveryapp.Entity.CartItem;
import com.projectla.deliveryapp.Entity.Order;
import com.projectla.deliveryapp.Entity.OrderItem;
import com.projectla.deliveryapp.Enum.OrderStatus;
import com.projectla.deliveryapp.Repository.CartRepository;
import com.projectla.deliveryapp.Repository.OrderRepository;
import com.projectla.deliveryapp.exception.BadRequestException;
import com.projectla.deliveryapp.exception.ResourceNotFoundException;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(CartRepository cartRepository,
                        OrderRepository orderRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    // 🔹 PLACE ORDER
    public OrderResponse placeOrder(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());

        double total = 0.0;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(cartItem.getProduct().getName());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setOrder(order);

            order.getItems().add(orderItem);

            total += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);

        // Clear cart after checkout
        cart.getItems().clear();
        cartRepository.save(cart);

        return mapToOrderResponse(savedOrder);
    }

    // 🔹 GET ALL ORDERS FOR USER
    public List<OrderResponse> getOrders(Long userId) {

        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream()
                .map(this::mapToOrderResponse)
                .toList();
    }

    // 🔹 GET SINGLE ORDER
    public OrderResponse getOrderById(Long userId, Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized order access");
        }

        return mapToOrderResponse(order);
    }

    // 🔹 UPDATE ORDER STATUS
    public OrderResponse updateOrderStatus(Long userId,
                                           Long orderId,
                                           OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (!order.getUser().getId().equals(userId)) {
            throw new BadRequestException("Unauthorized order access");
        }

        order.setStatus(status);

        Order updated = orderRepository.save(order);

        return mapToOrderResponse(updated);
    }

    // 🔹 PRIVATE MAPPER
    private OrderResponse mapToOrderResponse(Order order) {

        List<OrderItemResponse> items = order.getItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt(),
                items
        );
    }
}