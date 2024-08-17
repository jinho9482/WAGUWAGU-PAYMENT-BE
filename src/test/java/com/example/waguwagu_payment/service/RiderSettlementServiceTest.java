package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import com.example.waguwagu_payment.domain.type.RiderTransportation;
import com.example.waguwagu_payment.global.exception.PaymentNotFoundException;
import com.example.waguwagu_payment.global.exception.RiderSettlementNotFoundException;
import com.example.waguwagu_payment.global.repository.RiderSettlementRepository;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.kafka.dto.RiderIncome;
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
class RiderSettlementServiceTest {
    @Autowired
    private RiderSettlementService riderSettlementService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RiderSettlementRepository riderSettlementRepository;
    @BeforeEach
    void saveRiderSettlement() {
        UUID orderId = UUID.randomUUID();
        PaymentRequest req = new PaymentRequest(1L, orderId);
        paymentService.createPayment(req);

        PaymentResponse res = paymentService.getByOrderId(orderId);
        Payment payment = Payment.builder().id(1L).build();

        RiderSettlement riderSettlement = RiderSettlement
                .builder()
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .profit(100000)
                .incomeTax(3000)
                .residentTax(300)
                .payment(payment)
                .build();

        riderSettlementRepository.save(riderSettlement);
    }

    @Test
    @DisplayName("Success : should fetch same as saved record")
    @Transactional
    void createRiderSettlement() {
        UUID orderId = UUID.randomUUID();
        PaymentRequest paymentRequest = new PaymentRequest(40L, orderId);
        paymentService.createPayment(paymentRequest);

        PaymentResponse res = paymentService.getByOrderId(orderId);
        Long paymentId = res.id();

        RiderSettlementRequest riderSettlementRequest = new RiderSettlementRequest(
            200000, 6000, 600, paymentId
        );
        riderSettlementService.createRiderSettlement(riderSettlementRequest);
        RiderSettlement riderSettlement = riderSettlementRepository.findByPaymentIdAndDeletedFalse(paymentId)
                .orElseThrow(RiderSettlementNotFoundException::new);

        assertNotNull(riderSettlement);
        assertEquals(200000, riderSettlement.getProfit());
        assertFalse(riderSettlement.isDeleted());
        assertEquals(paymentId, riderSettlement.getPayment().getId());
    }

    @Nested
    @Transactional
    class getById {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        void success() {
            List<RiderSettlement> riderSettlements = riderSettlementRepository.findAll();
            int lastIndex = riderSettlements.size()-1;
            UUID savedId = riderSettlements.get(lastIndex).getId();
            RiderSettlementResponse res = riderSettlementService.getById(savedId);

            assertNotNull(res);
            assertEquals(3000, res.incomeTax());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        void fail() {
            assertThrows(RiderSettlementNotFoundException.class,
                    () -> riderSettlementService.getById(UUID.randomUUID()));
        }
    }

    @Nested
    @Transactional
    class getByPaymentId {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        void success() {
            List<RiderSettlement> riderSettlements = riderSettlementRepository.findAll();
            int lastIndex = riderSettlements.size()-1;
            Long savedPaymentId = riderSettlements.get(lastIndex).getPayment().getId();
            RiderSettlementResponse res = riderSettlementService.getByPaymentId(savedPaymentId);

            assertNotNull(res);
            assertEquals(3000, res.incomeTax());
        }
        @Test
        @DisplayName("Fail : should throw an error when payment id doesn't exist")
        void fail() {
            assertThrows(RiderSettlementNotFoundException.class,
                    () -> riderSettlementService.getByPaymentId(600L));
        }
    }

    @Nested
    @Transactional
    class deleteById {
        @Test
        @DisplayName("Success : should change deleted true")
        void success() {
            List<RiderSettlement> riderSettlements = riderSettlementRepository.findAll();
            int lastIndex = riderSettlements.size()-1;
            UUID savedId = riderSettlements.get(lastIndex).getId();

            riderSettlementService.deleteById(savedId);

            RiderSettlement riderSettlement = riderSettlementRepository.findById(savedId)
                    .orElseThrow(RiderSettlementNotFoundException::new);

            assertNotNull(riderSettlement);
            assertTrue(riderSettlement.isDeleted());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        void fail() {
            assertThrows(RiderSettlementNotFoundException.class,
                    () -> riderSettlementService.deleteById(UUID.randomUUID()));
        }
    }
    @Nested
    @Transactional
    class deleteByPaymentId {
        @Test
        @DisplayName("Success : should change deleted true")
        void success() {
            List<RiderSettlement> riderSettlements = riderSettlementRepository.findAll();
            int lastIndex = riderSettlements.size()-1;
            Long savedPaymentId = riderSettlements.get(lastIndex).getPayment().getId();
            UUID savedId = riderSettlements.get(lastIndex).getId();

            riderSettlementService.deleteByPaymentId(savedPaymentId);

            RiderSettlement riderSettlement = riderSettlementRepository.findById(savedId)
                    .orElseThrow(RiderSettlementNotFoundException::new);

            assertNotNull(riderSettlement);
            assertTrue(riderSettlement.isDeleted());
        }
        @Test
        @DisplayName("Fail : should throw an error when payment Id doesn't exist")
        void fail() {
            assertThrows(RiderSettlementNotFoundException.class,
                    () -> riderSettlementService.deleteByPaymentId(500L));
        }
    }

    @Test
    @DisplayName("Success : should calculate money properly")
    @Transactional
    void calculateMoneyForRider() {
        OrderInfo orderInfo = new OrderInfo(
                UUID.randomUUID(),
                1L,
                5000,
                "짱구할아버지",
                2L,
                15000,
                RiderTransportation.MOTORBIKE,
                3
        );

        RiderIncome riderIncome = riderSettlementService.calculateMoneyForRider(orderInfo);

        assertEquals(267, riderIncome.incomeTax());
        assertEquals(26, riderIncome.residentTax());
        assertEquals(8607, riderIncome.profit());
    }
}