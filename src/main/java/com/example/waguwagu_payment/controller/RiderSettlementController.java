package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.service.RiderSettlementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rider-settlements")
@RequiredArgsConstructor
public class RiderSettlementController {
    private final RiderSettlementService riderSettlementService;

    @PostMapping
    public void createRiderSettlement(@RequestBody RiderSettlementRequest req) {
        riderSettlementService.createRiderSettlement(req);
    }

    @GetMapping("/id/{id}")
    public RiderSettlementResponse getById(@PathVariable UUID id) {
        RiderSettlementResponse riderSettlementResponse = riderSettlementService.getById(id);
        return riderSettlementResponse;
    }

    @GetMapping("/paymentId/{paymentId}")
    public RiderSettlementResponse getByPaymentId(@PathVariable UUID paymentId) {
        RiderSettlementResponse riderSettlementResponse = riderSettlementService.getByPaymentId(paymentId);
        return riderSettlementResponse;
    }

    @DeleteMapping("/id/{id}")
    public void deleteById(@PathVariable UUID id) {
        riderSettlementService.deleteById(id);
    }

    @DeleteMapping("/paymentId/{paymentId}")
    public void deleteByPaymentId(@PathVariable UUID paymentId) {
        riderSettlementService.deleteByPaymentId(paymentId);
    }
}
