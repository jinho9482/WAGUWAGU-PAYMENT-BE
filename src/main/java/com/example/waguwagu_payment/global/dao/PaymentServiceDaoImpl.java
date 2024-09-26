package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.global.exception.PaymentNotFoundException;
import com.example.waguwagu_payment.global.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentServiceDaoImpl implements PaymentServiceDao {
    private final PaymentRepository paymentRepository;

    @Override
    public void save(PaymentRequest req) {
        paymentRepository.save(req.toEntity());
    }

    @Override
    public Payment findById(Long id) {
        Payment payment = paymentRepository.findByIdAndCanceledFalse(id)
                .orElseThrow(PaymentNotFoundException::new);
        return payment;
    }

    @Override
    public Payment findByOrderId(UUID orderId) {
        Payment payment = paymentRepository.findByOrderIdAndCanceledFalse(orderId)
                .orElseThrow(PaymentNotFoundException::new);
        return payment;
    }

//    @Override
//    public void deleteById(UUID id) {
//        Payment payment = paymentRepository.findByIdAndIsCanceledFalse(id)
//                .orElseThrow(PaymentNotFoundException::new);
//        paymentRepository.deleteById(id);
//    }
//
//    @Override
//    public void deleteByOrderId(UUID orderId) {
//        Payment payment = paymentRepository.findByOrderIdAndIsCanceledFalse(orderId)
//                .orElseThrow(PaymentNotFoundException::new);
//        paymentRepository.deleteByOrderId(orderId);
//    }
}
