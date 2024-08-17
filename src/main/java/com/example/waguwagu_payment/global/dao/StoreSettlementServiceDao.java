package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;


import java.util.UUID;

public interface StoreSettlementServiceDao {
    void save(StoreSettlementRequest req);
    StoreSettlement findById(UUID id);
    StoreSettlement findByPaymentId(Long paymentId);
}
