package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;

import java.util.UUID;


public interface PaymentService {
    void createPayment(PaymentRequest req);
    PaymentResponse getById(UUID id);
    PaymentResponse getByOrderId(UUID orderId);
    void cancelPaymentById(UUID id);
    void cancelPaymentByOrderId(UUID orderId);
}
