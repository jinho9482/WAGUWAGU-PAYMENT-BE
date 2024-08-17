package com.example.waguwagu_payment.controller;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    //
    @PostMapping
    public void createPayment(@RequestBody PaymentRequest req) {
        paymentService.createPayment(req);
    }

    @GetMapping("/id/{id}")
    public PaymentResponse getById(@PathVariable Long id) {
        PaymentResponse paymentResponse = paymentService.getById(id);
        return paymentResponse;
    }

    @GetMapping("/orderId/{orderId}")
    public PaymentResponse getByOrderId(@PathVariable UUID orderId) {
        PaymentResponse paymentResponse = paymentService.getByOrderId(orderId);
        return paymentResponse;
    }

    @DeleteMapping("/id/{id}")
    public void cancelPaymentById(@PathVariable Long id) {
        paymentService.cancelPaymentById(id);
    }

    @DeleteMapping("/orderId/{orderId}")
    public void cancelPaymentByOrderId(@PathVariable UUID orderId) {
        paymentService.cancelPaymentByOrderId(orderId);
    }
}
