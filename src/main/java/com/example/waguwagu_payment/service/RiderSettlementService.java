package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.kafka.dto.RiderIncome;
import java.util.UUID;

public interface RiderSettlementService {
    void createRiderSettlement(RiderSettlementRequest req);
    RiderSettlementResponse getById(UUID id);
    RiderSettlementResponse getByPaymentId(Long paymentId);
    void deleteById(UUID id);
    void deleteByPaymentId(Long paymentId);
    RiderIncome calculateMoneyForRider(OrderInfo dto);
}
