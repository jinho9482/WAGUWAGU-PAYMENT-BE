package com.example.waguwagu_payment.global.repository;

import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RiderSettlementRepository extends JpaRepository<RiderSettlement, UUID> {
    Optional<RiderSettlement> findByIdAndDeletedFalse(UUID id);
    Optional<RiderSettlement> findByPaymentIdAndDeletedFalse(Long paymentId);
}
