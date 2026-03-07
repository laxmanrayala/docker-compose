package com.projectla.deliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectla.deliveryapp.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
