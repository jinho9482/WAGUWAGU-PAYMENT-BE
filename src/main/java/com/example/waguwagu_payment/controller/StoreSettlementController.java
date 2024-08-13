package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreSettlementResponse;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.service.RiderSettlementService;
import com.example.waguwagu_payment.service.StoreSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/store-settlements")
@RequiredArgsConstructor
public class StoreSettlementController {
    private final StoreSettlementService storeSettlementService;

    @PostMapping
    public void createStoreSettlement(@RequestBody StoreSettlementRequest req) {
        storeSettlementService.createStoreSettlement(req);
    }
    @GetMapping("/id/{id}")
    public StoreSettlementResponse getById(@PathVariable UUID id) {
        StoreSettlementResponse storeSettlementResponse = storeSettlementService.getById(id);
        return storeSettlementResponse;
    }
    @GetMapping("/paymentId/{paymentId}")
    public StoreSettlementResponse getByPaymentId(@PathVariable UUID paymentId) {
        return storeSettlementService.getByPaymentId(paymentId);
    }
    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable UUID id) {
        storeSettlementService.deleteById(id);
    }
    @DeleteMapping("/paymentId/{paymentId}")
    public void deleteByPaymentId(@PathVariable UUID paymentId) {
        storeSettlementService.deleteByPaymentId(paymentId);
    }

}
