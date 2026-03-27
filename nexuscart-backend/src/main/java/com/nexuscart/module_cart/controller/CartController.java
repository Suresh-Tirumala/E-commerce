package com.nexuscart.module_cart.controller;

import com.nexuscart.module_cart.dto.CartItemRequest;
import com.nexuscart.module_cart.dto.CartResponse;
import com.nexuscart.module_cart.service.CartService;
import com.nexuscart.security.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * Shopping Cart Controller
 *
 * Handles cart operations including add, update, remove items.
 */
@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /**
     * Get current user's cart
     */
    @GetMapping
    public ResponseEntity<CartResponse> getUserCart(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CartResponse cart = cartService.getUserCart(userDetails.getId());
        return ResponseEntity.ok(cart);
    }

    /**
     * Add item to cart
     */
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItemToCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CartItemRequest request) {
        CartResponse cart = cartService.addItemToCart(
                userDetails.getId(), 
                request.getVariantId(), 
                request.getQuantity()
        );
        return ResponseEntity.ok(cart);
    }

    /**
     * Update item quantity in cart
     */
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateItemQuantity(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        CartResponse cart = cartService.updateItemQuantity(
                userDetails.getId(), 
                itemId, 
                quantity
        );
        return ResponseEntity.ok(cart);
    }

    /**
     * Remove item from cart
     */
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItemFromCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long itemId) {
        CartResponse cart = cartService.removeItemFromCart(
                userDetails.getId(), 
                itemId
        );
        return ResponseEntity.ok(cart);
    }

    /**
     * Clear all items from cart
     */
    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        cartService.clearCart(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
