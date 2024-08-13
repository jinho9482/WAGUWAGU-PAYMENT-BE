package com.example.waguwagu_payment.global.exception;

public class StoreSettlementNotFoundException extends IllegalArgumentException {
    public StoreSettlementNotFoundException() {
        super("Settlement for store is not found.");
        super.printStackTrace();
    }
}
