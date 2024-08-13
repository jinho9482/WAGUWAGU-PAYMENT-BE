package com.example.waguwagu_payment.domain.dto.request;

import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import java.sql.Timestamp;
import java.util.UUID;

public record RiderSettlementRequest(
    int profit,
    int incomeTax,
    int residentTax,
    UUID paymentId
) {
    public RiderSettlement toEntity(Payment payment) {
        RiderSettlement riderSettlement = RiderSettlement.builder()
                .profit(profit)
                .incomeTax(incomeTax)
                .residentTax(residentTax)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .payment(payment)
                .build();
        return riderSettlement;
    }
}
