package com.example.waguwagu_payment.domain.dto.response;

public record StoreCalculationResponse(
    int profit,
    int commission,
    int deliveryFee,
    int valueAddedTax
) {
}
