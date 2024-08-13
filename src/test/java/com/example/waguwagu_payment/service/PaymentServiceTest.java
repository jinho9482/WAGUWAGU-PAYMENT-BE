package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.global.exception.PaymentNotFoundException;
import com.example.waguwagu_payment.global.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private PaymentRepository paymentRepository;
    private final UUID ORDER_ID = UUID.randomUUID();

    @BeforeEach
    void savePayment() {
        Payment payment = Payment.builder()
                .orderId(ORDER_ID)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
        paymentRepository.save(payment);
    }

    @Test
    @Transactional
    @DisplayName("Success : should fetch same as saved record")
    void createPayment() {
        UUID orderId = UUID.randomUUID();
        PaymentRequest req = new PaymentRequest(orderId);
        paymentService.createPayment(req);

        Payment payment = paymentRepository.findByOrderIdAndCanceledFalse(req.orderId())
                .orElseThrow(PaymentNotFoundException::new);

        assertNotNull(payment);
        assertEquals(orderId, payment.getOrderId());
    }

    @Nested
    class getById {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        @Transactional
        void success() {
            List<Payment> payments = paymentRepository.findAll();
            int lastIndex = payments.size()-1;
            UUID savedId = payments.get(lastIndex).getId();
            PaymentResponse res = paymentService.getById(savedId);

            assertNotNull(res);
            assertEquals(ORDER_ID, res.orderId());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        @Transactional
        void fail() {
            assertThrows(PaymentNotFoundException.class, () -> paymentService.getById(UUID.randomUUID()));
        }
    }

    @Nested
    class getByOrderId {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        @Transactional
        void success() {
            PaymentResponse res = paymentService.getByOrderId(ORDER_ID);

            assertNotNull(res);
            assertEquals(ORDER_ID, res.orderId());
        }
        @Test
        @DisplayName("Fail : should throw an error when order id doesn't exist")
        @Transactional
        void fail() {
            assertThrows(PaymentNotFoundException.class, () -> paymentService.getByOrderId(UUID.randomUUID()));
        }
    }

    @Nested
    class cancelPaymentById {
        @Test
        @DisplayName("Success : should change canceled true")
        @Transactional

        void success() {
            List<Payment> payments = paymentRepository.findAll();
            int lastIndex = payments.size()-1;
            UUID savedId = payments.get(lastIndex).getId();
            paymentService.cancelPaymentById(savedId);
            Payment payment = paymentRepository.findById(savedId).orElseThrow(PaymentNotFoundException::new);

            assertNotNull(payment);
            assertTrue(payment.isCanceled());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        @Transactional

        void fail() {
            assertThrows(PaymentNotFoundException.class, () -> paymentService.cancelPaymentById(UUID.randomUUID()));
        }
    }

    @Nested
    class cancelPaymentByOrderId {
        @Test
        @DisplayName("Success : should change canceled true")
        @Transactional
        void success() {
            paymentService.cancelPaymentByOrderId(ORDER_ID);
            List<Payment> payments = paymentRepository.findAll();
            int lastIndex = payments.size()-1;
            UUID savedId = payments.get(lastIndex).getId();
            Payment payment = paymentRepository.findById(savedId).orElseThrow(PaymentNotFoundException::new);

            assertNotNull(payment);
            assertTrue(payment.isCanceled());
        }
        @Test
        @DisplayName("Fail : should throw an error when order id doesn't exist")
        @Transactional
        void fail() {
            assertThrows(PaymentNotFoundException.class, () -> paymentService.cancelPaymentByOrderId(UUID.randomUUID()));
        }
    }
}