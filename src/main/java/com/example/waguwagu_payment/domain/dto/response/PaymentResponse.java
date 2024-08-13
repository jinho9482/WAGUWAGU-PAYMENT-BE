package com.example.waguwagu_payment.domain.dto.response;

import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import jakarta.persistence.Column;

import java.sql.Timestamp;
import java.util.UUID;

public record PaymentResponse(
    UUID id,
    UUID orderId,
    Timestamp createdAt
) {
    public static PaymentResponse from(Payment payment) {
        PaymentResponse res = new PaymentResponse(
            payment.getId(),
            payment.getOrderId(),
            payment.getCreatedAt()
        );
        return res;
    } 
}
