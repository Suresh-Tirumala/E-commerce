package com.nexuscart.module_cart.service;

import com.nexuscart.common.exception.ResourceNotFoundException;
import com.nexuscart.module_auth.entity.User;
import com.nexuscart.module_auth.repository.UserRepository;
import com.nexuscart.module_cart.dto.CartResponse;
import com.nexuscart.module_cart.entity.Cart;
import com.nexuscart.module_cart.entity.CartItem;
import com.nexuscart.module_cart.repository.CartRepository;
import com.nexuscart.module_product.entity.ProductVariant;
import com.nexuscart.module_product.repository.ProductVariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;

    @Transactional(readOnly = true)
    public CartResponse getUserCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseGet(() -> createNewCart(user));

        return mapToResponse(cart);
    }

    public CartResponse addItemToCart(Long userId, Long variantId, Integer quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Product variant not found with id: " + variantId));

        if (variant.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for variant: " + variant.getSku());
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createNewCart(user));

        // Check if item already exists in cart
        CartItem existingItem = cart.getItems().stream()
                .filter(item -> item.getVariant().getId().equals(variantId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantity;
            if (variant.getStockQuantity() < newQuantity) {
                throw new IllegalArgumentException("Insufficient stock for requested quantity");
            }
            existingItem.setQuantity(newQuantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .variant(variant)
                    .quantity(quantity)
                    .priceAtAddition(variant.getPriceOverride() != null ? 
                            variant.getPriceOverride() : variant.getProduct().getBasePrice())
                    .build();
            cart.addItem(newItem);
        }

        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    public CartResponse updateItemQuantity(Long userId, Long itemId, Integer quantity) {
        if (quantity <= 0) {
            return removeItemFromCart(userId, itemId);
        }

        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        ProductVariant variant = item.getVariant();
        if (variant.getStockQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock for requested quantity");
        }

        item.setQuantity(quantity);
        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    public CartResponse removeItemFromCart(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        cart.removeItem(item);
        Cart savedCart = cartRepository.save(cart);
        return mapToResponse(savedCart);
    }

    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));

        cart.getItems().clear();
        cartRepository.save(cart);
    }

    private Cart createNewCart(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .items(new ArrayList<>())
                .build();
        return cartRepository.save(cart);
    }

    private CartResponse mapToResponse(Cart cart) {
        List<CartResponse.CartItemDetail> itemDetails = cart.getItems().stream()
                .map(item -> {
                    BigDecimal price = item.getPriceAtAddition() != null ? 
                            item.getPriceAtAddition() : item.getVariant().getPriceOverride() != null ?
                            item.getVariant().getPriceOverride() : item.getVariant().getProduct().getBasePrice();
                    BigDecimal subtotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));

                    String imageUrl = item.getVariant().getProduct().getImages().isEmpty() ? null :
                            item.getVariant().getProduct().getImages().get(0).getImageUrl();

                    return CartResponse.CartItemDetail.builder()
                            .itemId(item.getId())
                            .variantId(item.getVariant().getId())
                            .productName(item.getVariant().getProduct().getName())
                            .variantName(getVariantName(item.getVariant()))
                            .imageUrl(imageUrl)
                            .quantity(item.getQuantity())
                            .price(price)
                            .subtotal(subtotal)
                            .build();
                })
                .toList();

        BigDecimal subtotal = itemDetails.stream()
                .map(CartResponse.CartItemDetail::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = itemDetails.stream()
                .mapToInt(CartResponse.CartItemDetail::getQuantity)
                .sum();

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .items(itemDetails)
                .totalItems(totalItems)
                .subtotal(subtotal)
                .updatedAt(cart.getUpdatedAt())
                .build();
    }

    private String getVariantName(ProductVariant variant) {
        if (variant.getAttributes() == null || variant.getAttributes().isEmpty()) {
            return variant.getSku();
        }
        return variant.getAttributes().entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .reduce((a, b) -> a + ", " + b)
                .orElse(variant.getSku());
    }
}
