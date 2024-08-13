package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;

import java.util.UUID;

public interface RiderSettlementServiceDao {
    void save(RiderSettlementRequest req);
    RiderSettlement findById(UUID id);
    RiderSettlement findByPaymentId(UUID paymentId);
}
