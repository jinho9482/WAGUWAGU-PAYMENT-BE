package com.example.waguwagu_payment.service;


import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.global.dao.PaymentServiceDao;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentServiceDao paymentServiceDao;


    @Override
    public void createPayment(PaymentRequest req) {
        paymentServiceDao.save(req);
    }

    @Override
    public PaymentResponse getById(Long id) {
        Payment payment = paymentServiceDao.findById(id);
        PaymentResponse res = PaymentResponse.from(payment);
        return res;
    }

    @Override
    public PaymentResponse getByOrderId(UUID orderId) {
        Payment payment = paymentServiceDao.findByOrderId(orderId);
        PaymentResponse res = PaymentResponse.from(payment);
        return res;
    }

    @Override
    public void cancelPaymentById(Long id) {
        Payment payment = paymentServiceDao.findById(id);
        payment.setCanceled(true);
    }

    @Override
    public void cancelPaymentByOrderId(UUID orderId) {
        Payment payment = paymentServiceDao.findByOrderId(orderId);
        payment.setCanceled(true);
    }



//    // 라이더에게 정산액 전달
//    public void sendPaymentInfoToRider(RiderIncome riderIncome) {
//        deliveryIncomeProducer.send(riderIncome, "insert");
//    }
//
//    // 가게에 정산액 전달
//    public void sendPaymentInfoToStore(StoreIncome storeIncome) {
//        storeIncomeProducer.send(storeIncome, "insert");
//    }

}
