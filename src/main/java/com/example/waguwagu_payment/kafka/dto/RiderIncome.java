package com.example.waguwagu_payment.kafka.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record RiderIncome(
//        int deliveryIncome, // 라이더 쪽에서 필드명을 바꿔야 함
        int profit,
        int incomeTax,
        int residentTax,
        String storeName,
        UUID orderId,
        Timestamp createdAt

) {
}
