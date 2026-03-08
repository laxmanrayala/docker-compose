package com.projectla.deliveryapp.Service;

import com.projectla.deliveryapp.Dto.CartItemResponse;
import com.projectla.deliveryapp.Dto.CartResponse;
import com.projectla.deliveryapp.Entity.*;
import com.projectla.deliveryapp.Repository.*;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    // =========================
    // ADD TO CART
    // =========================

    public CartResponse addToCart(Long userId, Long productId, Integer quantity) {

    if (quantity == null || quantity <= 0) {
        throw new RuntimeException("Invalid quantity");
    }

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

        // update quantity
        cartItem.setQuantity(cartItem.getQuantity() + quantity);

    } else {

        // create new item
        cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

    }

    cartItemRepository.save(cartItem);

    return getCart(userId);
}
    // =========================
    // REDUCE QUANTITY
    // =========================

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

        if (quantity >= currentQty) {

            cartItemRepository.delete(cartItem);

            if (cart.getItems() != null) {
                cart.getItems().remove(cartItem);
            }

        } else {

            cartItem.setQuantity(currentQty - quantity);
            cartItemRepository.save(cartItem);
        }

        return getCart(userId);
    }

    // =========================
    // REMOVE ITEM COMPLETELY
    // =========================

    public CartResponse removeItem(Long userId, Long productId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not in cart"));

        cartItemRepository.delete(cartItem);

        if (cart.getItems() != null) {
            cart.getItems().remove(cartItem);
        }

        return getCart(userId);
    }

    // =========================
    // CLEAR CART
    // =========================

    public CartResponse clearCart(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() != null) {

            cartItemRepository.deleteAll(cart.getItems());

            cart.getItems().clear();
        }

        return getCart(userId);
    }

    // =========================
    // GET CART
    // =========================

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

    // =========================
    // CREATE CART
    // =========================

    private Cart createCart(User user) {

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());

        return cartRepository.save(cart);
    }

    // =========================
    // DEBUG
    // =========================

    public Cart debugCart(Long userId) {

        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}