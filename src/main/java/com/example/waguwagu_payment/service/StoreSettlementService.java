package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreSettlementResponse;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.kafka.dto.StoreIncome;

import java.util.UUID;

public interface StoreSettlementService {
    void createStoreSettlement(StoreSettlementRequest req);
    StoreSettlementResponse getById(UUID id);
    StoreSettlementResponse getByPaymentId(Long paymentId);
    void deleteById(UUID id);
    void deleteByPaymentId(Long paymentId);
//    StoreIncome calculateMoneyForStore(OrderInfo dto);

    StoreCalculationResponse calculateMoneyForStore(OrderInfo dto);
}
