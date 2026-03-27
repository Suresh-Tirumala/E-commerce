package com.nexuscart.module_payment.service;

import com.nexuscart.common.exception.ResourceNotFoundException;
import com.nexuscart.module_order.entity.Order;
import com.nexuscart.module_order.repository.OrderRepository;
import com.nexuscart.module_payment.dto.PaymentRequest;
import com.nexuscart.module_payment.dto.PaymentResponse;
import com.nexuscart.module_payment.entity.Payment;
import com.nexuscart.module_payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Mock Payment Service for development and testing.
 * In production, replace with actual Stripe/Razorpay integration.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MockPaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    /**
     * Process a mock payment.
     * Simulates success/failure based on amount (odd = success, even = failure for demo).
     */
    @Transactional
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing mock payment for order: {}", request.getOrderId());

        // Fetch order
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", request.getOrderId()));

        // Validate order status
        if (!Order.OrderStatus.PENDING.equals(order.getStatus())) {
            throw new IllegalStateException("Order is not in PENDING status. Current status: " + order.getStatus());
        }

        // Create pending payment
        Payment payment = Payment.builder()
                .order(order)
                .provider("MOCK")
                .amount(order.getTotalAmount())
                .status(Payment.PaymentStatus.PROCESSING)
                .paymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "CARD")
                .currency("USD")
                .build();

        paymentRepository.save(payment);

        // Simulate payment processing
        boolean isSuccess = simulatePaymentProcessing(order.getTotalAmount());

        if (isSuccess) {
            // Generate mock transaction ID
            String transactionId = "TXN_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
            
            payment.setTransactionId(transactionId);
            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            payment.setPayload("{\"mock_response\": \"success\", \"auth_code\": \"AUTH123\"}");
            
            // Update order status to CONFIRMED
            order.setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(order);
            
            log.info("Payment successful for order {}: Transaction ID {}", order.getId(), transactionId);
        } else {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason("Mock payment failure - even amount");
            payment.setPayload("{\"mock_response\": \"failed\", \"error_code\": \"INSUFFICIENT_FUNDS\"}");
            
            // Keep order as PENDING or mark as PAYMENT_FAILED based on business logic
            order.setStatus(Order.OrderStatus.PENDING); // Or create a new status
            orderRepository.save(order);
            
            log.warn("Payment failed for order {}: Mock failure", order.getId());
        }

        paymentRepository.save(payment);
        return PaymentResponse.fromEntity(payment);
    }

    /**
     * Get payment details by order ID.
     */
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "orderId", orderId));
        
        return PaymentResponse.fromEntity(payment);
    }

    /**
     * Simulate payment processing logic.
     * For demo: odd amounts succeed, even amounts fail.
     */
    private boolean simulatePaymentProcessing(java.math.BigDecimal amount) {
        // Simple simulation: use the integer part of amount
        int amountInt = amount.intValue();
        boolean isSuccess = (amountInt % 2 != 0);
        
        log.debug("Simulating payment for amount {}: Result {}", amount, isSuccess ? "SUCCESS" : "FAILURE");
        return isSuccess;
    }

    /**
     * Mock webhook handler for payment provider callbacks.
     */
    @Transactional
    public void handleWebhook(String transactionId, String status, String payload) {
        log.info("Received webhook for transaction: {}", transactionId);
        
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "transactionId", transactionId));

        if ("SUCCESS".equalsIgnoreCase(status)) {
            payment.setStatus(Payment.PaymentStatus.SUCCESS);
            payment.getOrder().setStatus(Order.OrderStatus.CONFIRMED);
            orderRepository.save(payment.getOrder());
        } else if ("FAILED".equalsIgnoreCase(status)) {
            payment.setStatus(Payment.PaymentStatus.FAILED);
            payment.setFailureReason("Webhook failure notification");
        }

        payment.setPayload(payload);
        paymentRepository.save(payment);
        
        log.info("Webhook processed for transaction {}: New status {}", transactionId, payment.getStatus());
    }
}
