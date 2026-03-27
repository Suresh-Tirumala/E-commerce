package com.nexuscart.module_payment.controller;

import com.nexuscart.module_payment.dto.PaymentRequest;
import com.nexuscart.module_payment.dto.PaymentResponse;
import com.nexuscart.module_payment.service.MockPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling payment operations.
 * Uses mock payment service for development.
 */
@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
@RequiredArgsConstructor
public class PaymentController {

    private final MockPaymentService mockPaymentService;

    /**
     * Process a payment for an order.
     * Customer can only pay for their own orders.
     */
    @PostMapping("/process")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<PaymentResponse> processPayment(
            @Valid @RequestBody PaymentRequest request) {
        
        log.info("Payment request received for order: {}", request.getOrderId());
        
        PaymentResponse response = mockPaymentService.processPayment(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get payment details by order ID.
     */
    @GetMapping("/order/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable Long orderId) {
        
        PaymentResponse response = mockPaymentService.getPaymentByOrderId(orderId);
        
        return ResponseEntity.ok(response);
    }

    /**
     * Mock webhook endpoint for payment provider callbacks.
     * In production, this would be secured with provider-specific signatures.
     */
    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(
            @RequestParam String transactionId,
            @RequestParam String status,
            @RequestBody String payload) {
        
        log.info("Webhook received - Transaction: {}, Status: {}", transactionId, status);
        
        mockPaymentService.handleWebhook(transactionId, status, payload);
        
        return ResponseEntity.ok().build();
    }

    /**
     * Admin endpoint to view all payments (for monitoring).
     */
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> getAllPayments() {
        // In production, implement pagination and filtering
        return ResponseEntity.ok("Admin payment listing - Implement pagination as needed");
    }
}
