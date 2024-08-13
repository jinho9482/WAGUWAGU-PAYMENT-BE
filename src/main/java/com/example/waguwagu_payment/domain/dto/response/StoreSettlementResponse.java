package com.example.waguwagu_payment.domain.dto.response;

import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;

import java.sql.Timestamp;
import java.util.UUID;

public record StoreSettlementResponse(
        UUID id,
        int profit,
        int commission,
        int deliveryFee,
        int valueAddedTax,
        Timestamp createdAt,
        Payment payment
) {
    public static StoreSettlementResponse from(StoreSettlement storeSettlement) {
        StoreSettlementResponse res = new StoreSettlementResponse(
            storeSettlement.getId(),
            storeSettlement.getProfit(),
            storeSettlement.getCommission(),
            storeSettlement.getDeliveryFee(),
            storeSettlement.getValueAddedTax(),
            storeSettlement.getCreatedAt(),
            storeSettlement.getPayment()
        );
        return res;
    }
}
