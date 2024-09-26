package com.example.waguwagu_payment.global.repository;

import com.example.waguwagu_payment.domain.entity.StoreSettlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreSettlementRepository extends JpaRepository<StoreSettlement, UUID> {
    Optional<StoreSettlement> findByIdAndDeletedFalse(UUID id);
    Optional<StoreSettlement> findByPaymentIdAndDeletedFalse(Long paymentId);
}
