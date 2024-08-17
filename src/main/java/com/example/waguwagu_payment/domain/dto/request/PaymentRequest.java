package com.example.waguwagu_payment.domain.dto.request;

import com.example.waguwagu_payment.domain.entity.Payment;

import java.util.UUID;

public record PaymentRequest(
        Long id,
        UUID orderId
) {
    public Payment toEntity() {
        Payment payment = Payment.builder()
                .id(id)
                .orderId(orderId)
                .build();
        return payment;
    }
}
