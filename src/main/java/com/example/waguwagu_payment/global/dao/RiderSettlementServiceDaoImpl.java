package com.example.waguwagu_payment.global.dao;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import com.example.waguwagu_payment.global.exception.RiderSettlementNotFoundException;
import com.example.waguwagu_payment.global.repository.RiderSettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RiderSettlementServiceDaoImpl implements RiderSettlementServiceDao {
    private final RiderSettlementRepository riderSettlementRepository;

    @Override
    public void save(RiderSettlementRequest req) {
        Payment payment = Payment.builder()
                .id(req.paymentId())
                .build();
        riderSettlementRepository.save(req.toEntity(payment));
    }

    @Override
    public RiderSettlement findById(UUID id) {
        RiderSettlement riderSettlement = riderSettlementRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(RiderSettlementNotFoundException::new);
        return riderSettlement;
    }

    @Override
    public RiderSettlement findByPaymentId(Long paymentId) {
        RiderSettlement riderSettlement = riderSettlementRepository.findByPaymentIdAndDeletedFalse(paymentId)
                .orElseThrow(RiderSettlementNotFoundException::new);
        return riderSettlement;
    }
}
