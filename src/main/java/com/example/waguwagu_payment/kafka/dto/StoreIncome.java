package com.example.waguwagu_payment.kafka.dto;

import java.sql.Timestamp;

public record StoreIncome (
        Long storeId,
        Timestamp time,
        int sales
){
}
