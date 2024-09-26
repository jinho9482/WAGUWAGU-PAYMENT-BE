package com.example.waguwagu_payment.domain.dto.request;

import com.example.waguwagu_payment.domain.entity.Payment;
import lombok.NonNull;

import java.util.UUID;

public record PaymentRequest(
        @NonNull Long id,
        @NonNull UUID orderId
) {
    public Payment toEntity() {
        Payment payment = Payment.builder()
                .id(id)
                .orderId(orderId)
                .build();
        return payment;
    }
}
