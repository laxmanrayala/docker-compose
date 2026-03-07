package com.projectla.deliveryapp.Service;

import com.projectla.deliveryapp.Dto.CartItemResponse;
import com.projectla.deliveryapp.Dto.CartResponse;
import com.projectla.deliveryapp.Entity.*;
import com.projectla.deliveryapp.Repository.*;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository,
                       ProductRepository productRepository,
                       CartRepository cartRepository,
                       CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

public CartResponse addToCart(Long userId, Long productId, Integer quantity) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

    Cart cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUser(user);
                return cartRepository.save(newCart);
            });

    CartItem cartItem = cartItemRepository
            .findByCartIdAndProductId(cart.getId(), productId)
            .orElse(null);
            

    if (cartItem != null) {
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
    } else {
        cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getItems().add(cartItem);
    }

    cartItemRepository.save(cartItem);

    return getCart(userId);
}

public CartResponse removeItem(Long userId, Long productId) {

    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    CartItem cartItem = cartItemRepository
            .findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Product not in cart"));

    cartItemRepository.delete(cartItem);

    return getCart(userId);
}

public CartResponse clearCart(Long userId) {

    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    cartItemRepository.deleteAll(cart.getItems());

    return getCart(userId);
}

    public CartResponse getCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItemResponse> items = cart.getItems()
                .stream()
                .map(item -> new CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getPrice(),
                        item.getQuantity()
                ))
                .toList();

        return new CartResponse(cart.getId(), userId, items);
    }

    public CartResponse reduceQuantity(Long userId, Long productId, Integer quantity) {

    if (quantity == null || quantity <= 0) {
        throw new RuntimeException("Invalid quantity");
    }

    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));

    CartItem cartItem = cartItemRepository
            .findByCartIdAndProductId(cart.getId(), productId)
            .orElseThrow(() -> new RuntimeException("Product not in cart"));

    int currentQty = cartItem.getQuantity();

    if (quantity > currentQty) {
        throw new RuntimeException("Reduction exceeds current quantity");
    }

    if (quantity.equals(currentQty)) {
        cartItemRepository.delete(cartItem);
        cart.getItems().remove(cartItem);
    } else {
        cartItem.setQuantity(currentQty - quantity);
        cartItemRepository.save(cartItem);
    }

    return getCart(userId);
}

    public Cart debugCart(Long userId) {
    return cartRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Cart not found"));
}

}