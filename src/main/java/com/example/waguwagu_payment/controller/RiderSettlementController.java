package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.service.RiderSettlementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments/rider-settlements")
@RequiredArgsConstructor
@Tag(name = "배달 기사 정산")
public class RiderSettlementController {
    private final RiderSettlementService riderSettlementService;

    @PostMapping
    @Operation(summary = "배달 기사 정산 내역 생성")
    public void createRiderSettlement(@RequestBody RiderSettlementRequest req) {
        riderSettlementService.createRiderSettlement(req);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "정산 ID로 배달 기사 정산 내역 가져오기")
    public RiderSettlementResponse getById(@PathVariable UUID id) {
        RiderSettlementResponse riderSettlementResponse = riderSettlementService.getById(id);
        return riderSettlementResponse;
    }

    @GetMapping("/paymentId/{paymentId}")
    @Operation(summary = "결제 ID로 배달 기사 정산 내역 가져오기")
    public RiderSettlementResponse getByPaymentId(@PathVariable Long paymentId) {
        RiderSettlementResponse riderSettlementResponse = riderSettlementService.getByPaymentId(paymentId);
        return riderSettlementResponse;
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "정산 ID로 배달 기사 정산 내역 삭제")
    public void deleteById(@PathVariable UUID id) {
        riderSettlementService.deleteById(id);
    }

    @DeleteMapping("/paymentId/{paymentId}")
    @Operation(summary = "결제 ID로 배달 기사 정산 내역 삭제")
    public void deleteByPaymentId(@PathVariable Long paymentId) {
        riderSettlementService.deleteByPaymentId(paymentId);
    }
}
