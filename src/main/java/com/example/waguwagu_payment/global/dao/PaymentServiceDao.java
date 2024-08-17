package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.entity.Payment;

import java.util.UUID;

public interface PaymentServiceDao {
    void save(PaymentRequest payment);
    Payment findById(Long id);
    Payment findByOrderId(UUID orderId);
//    void deleteById(UUID id);
//    void deleteByOrderId(UUID orderId);
}
