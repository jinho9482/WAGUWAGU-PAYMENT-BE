package com.example.waguwagu_payment.global.exception;

public class RiderSettlementNotFoundException extends IllegalArgumentException {
    public RiderSettlementNotFoundException() {
        super("Settlement for rider is not found.");
        super.printStackTrace();
    }
}
