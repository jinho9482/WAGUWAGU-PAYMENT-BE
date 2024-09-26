package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreSettlementResponse;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.service.RiderSettlementService;
import com.example.waguwagu_payment.service.StoreSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments/store-settlements")
@RequiredArgsConstructor
@Tag(name = "가게 정산")
public class StoreSettlementController {
    private final StoreSettlementService storeSettlementService;

    @PostMapping
    @Operation(summary = "가게 정산 내역 생성")
    public void createStoreSettlement(@RequestBody StoreSettlementRequest req) {
        storeSettlementService.createStoreSettlement(req);
    }
    @GetMapping("/id/{id}")
    @Operation(summary = "정산 ID로 가게 정산 내역 가져오기")
    public StoreSettlementResponse getById(@PathVariable UUID id) {
        StoreSettlementResponse storeSettlementResponse = storeSettlementService.getById(id);
        return storeSettlementResponse;
    }
    @GetMapping("/paymentId/{paymentId}")
    @Operation(summary = "결제 ID로 가게 정산 내역 가져오기")
    public StoreSettlementResponse getByPaymentId(@PathVariable UUID paymentId) {
        return storeSettlementService.getByPaymentId(paymentId);
    }
    @DeleteMapping("/id/{id}")
    @Operation(summary = "정산 ID로 가게 정산 내역 삭제")
    public void deleteById(@PathVariable UUID id) {
        storeSettlementService.deleteById(id);
    }
    @DeleteMapping("/paymentId/{paymentId}")
    @Operation(summary = "결제 ID로 가게 정산 내역 삭제")
    public void deleteByPaymentId(@PathVariable UUID paymentId) {
        storeSettlementService.deleteByPaymentId(paymentId);
    }

}
