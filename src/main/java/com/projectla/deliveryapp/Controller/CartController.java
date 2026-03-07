package com.projectla.deliveryapp.Controller;

import com.projectla.deliveryapp.Dto.AddToCartRequest;
import com.projectla.deliveryapp.Dto.CartResponse;
import com.projectla.deliveryapp.Entity.Cart;
import com.projectla.deliveryapp.Service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public CartResponse addToCart(@PathVariable Long userId,
                            @RequestBody AddToCartRequest request) {

        return cartService.addToCart(userId, request.getProductId(), request.getQuantity());
    }

    @DeleteMapping("/remove/{productId}")
    public CartResponse removeItem(@PathVariable Long userId, @PathVariable Long productId) {

    return cartService.removeItem(userId, productId);
    }

    @DeleteMapping("/clear")
    public CartResponse clearCart(@PathVariable Long userId) {
        
        return cartService.clearCart(userId);
}

@GetMapping
public CartResponse getCart(@PathVariable Long userId) {
    return cartService.getCart(userId);
}

@PatchMapping("/reduce")
public CartResponse reduceQuantity(@PathVariable Long userId,
                                   @RequestBody AddToCartRequest request) {

    return cartService.reduceQuantity(
            userId,
            request.getProductId(),
            request.getQuantity()
    );
}

@GetMapping("/debug")
public Cart debug(@PathVariable Long userId) {
    return cartService.debugCart(userId);
}

}
