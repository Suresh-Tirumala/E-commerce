package com.nexuscart.module_payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for initiating a payment.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "Order ID is required")
    private Long orderId;

    private String paymentMethod; // CARD, UPI, WALLET
    
    // Optional fields for card payments (in real scenario, these come from Stripe Elements)
    private String cardNumber;
    private String expiryMonth;
    private String expiryYear;
    private String cvv;
}
