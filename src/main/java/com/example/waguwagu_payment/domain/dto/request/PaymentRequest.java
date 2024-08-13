package com.example.waguwagu_payment.domain.dto.request;

import com.example.waguwagu_payment.domain.entity.Payment;

import java.util.UUID;

public record PaymentRequest(
        UUID orderId
) {
    public Payment toEntity() {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .build();
        return payment;
    }
}
