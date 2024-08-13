package com.example.waguwagu_payment.domain.dto.request;

import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;

import java.sql.Timestamp;
import java.util.UUID;

public record StoreSettlementRequest(
    int profit,
    int commission,
    int deliveryFee,
    int valueAddedTax,
    UUID paymentId
) {
    public StoreSettlement toEntity(Payment payment) {
        StoreSettlement storeSettlement = StoreSettlement.builder()
                .profit(profit)
                .commission(commission)
                .deliveryFee(deliveryFee)
                .valueAddedTax(valueAddedTax)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .payment(payment)
                .build();
        return storeSettlement;
    }
}
