package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;

import java.util.UUID;


public interface PaymentService {
    void createPayment(PaymentRequest req);
    PaymentResponse getById(Long id);
    PaymentResponse getByOrderId(UUID orderId);
    void cancelPaymentById(Long id);
    void cancelPaymentByOrderId(UUID orderId);
}
