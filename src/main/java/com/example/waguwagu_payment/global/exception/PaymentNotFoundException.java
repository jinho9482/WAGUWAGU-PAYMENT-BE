package com.example.waguwagu_payment.global.exception;

public class PaymentNotFoundException extends IllegalArgumentException {
    public PaymentNotFoundException() {
        super("payment is not found.");
        super.printStackTrace();
    }
}
