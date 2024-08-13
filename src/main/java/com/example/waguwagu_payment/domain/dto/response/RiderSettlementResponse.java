package com.example.waguwagu_payment.domain.dto.response;

import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;

import java.sql.Timestamp;
import java.util.UUID;

public record RiderSettlementResponse(
        UUID id,
        Timestamp createdAt,
        int profit,
        int incomeTax,
        int residentTax,
        Payment payment
) {
    public static RiderSettlementResponse from(RiderSettlement riderSettlement) {
        RiderSettlementResponse res = new RiderSettlementResponse(
            riderSettlement.getId(),
            riderSettlement.getCreatedAt(),
            riderSettlement.getProfit(),
            riderSettlement.getIncomeTax(),
            riderSettlement.getResidentTax(),
            riderSettlement.getPayment()
        );
        return res;
    }
}
