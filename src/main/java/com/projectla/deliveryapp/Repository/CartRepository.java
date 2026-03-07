package com.projectla.deliveryapp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.projectla.deliveryapp.Entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserId(Long userId);
}
