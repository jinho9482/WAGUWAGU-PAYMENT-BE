package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;
import com.example.waguwagu_payment.global.exception.StoreSettlementNotFoundException;
import com.example.waguwagu_payment.global.repository.StoreSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StoreSettlementServiceDaoImpl implements StoreSettlementServiceDao {
    private final StoreSettlementRepository storeSettlementRepository;

    @Override
    public void save(StoreSettlementRequest req) {
        Payment payment = Payment.builder()
                .id(req.paymentId())
                .build();
        storeSettlementRepository.save(req.toEntity(payment));
    }

    @Override
    public StoreSettlement findById(UUID id) {
        StoreSettlement storeSettlement = storeSettlementRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(StoreSettlementNotFoundException::new);
        return storeSettlement;
    }

    @Override
    public StoreSettlement findByPaymentId(Long paymentId) {
        StoreSettlement storeSettlement = storeSettlementRepository.findByPaymentIdAndDeletedFalse(paymentId)
                .orElseThrow(StoreSettlementNotFoundException::new);
        return storeSettlement;
    }
}
