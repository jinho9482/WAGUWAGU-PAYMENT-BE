package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @Operation(summary = "결제 내역 생성")
    public void createPayment(@RequestBody PaymentRequest req) {
        paymentService.createPayment(req);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "결제 ID로 결제 내역 가져오기")
    public PaymentResponse getById(@PathVariable UUID id) {
        PaymentResponse paymentResponse = paymentService.getById(id);
        return paymentResponse;
    }

    @GetMapping("/orderId/{orderId}")
    @Operation(summary = "주문 ID로 결제 내역 가져오기")
    public PaymentResponse getByOrderId(@PathVariable UUID orderId) {
        PaymentResponse paymentResponse = paymentService.getByOrderId(orderId);
        return paymentResponse;
    }

    @DeleteMapping("/id/{id}")
    @Operation(summary = "결제 ID로 결제 내역 취소하기")
    public void cancelPaymentById(@PathVariable UUID id) {
        paymentService.cancelPaymentById(id);
    }

    @DeleteMapping("/orderId/{orderId}")
    @Operation(summary = "주문 ID로 결제 내역 취소하기")
    public void cancelPaymentByOrderId(@PathVariable UUID orderId) {
        paymentService.cancelPaymentByOrderId(orderId);
    }
}
