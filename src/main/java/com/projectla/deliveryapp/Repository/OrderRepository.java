package com.projectla.deliveryapp.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectla.deliveryapp.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
