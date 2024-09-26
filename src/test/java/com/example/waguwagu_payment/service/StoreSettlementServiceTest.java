package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.PaymentRequest;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreSettlementResponse;
import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;
import com.example.waguwagu_payment.domain.type.RiderTransportation;
import com.example.waguwagu_payment.global.exception.StoreSettlementNotFoundException;
import com.example.waguwagu_payment.global.repository.StoreSettlementRepository;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
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
class StoreSettlementServiceTest {
    @Autowired
    private StoreSettlementService storeSettlementService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private StoreSettlementRepository storeSettlementRepository;
    @BeforeEach
    void saveStoreSettlement() {
        UUID orderId = UUID.randomUUID();
        PaymentRequest req = new PaymentRequest(5L, orderId);
        paymentService.createPayment(req);

        PaymentResponse res = paymentService.getByOrderId(orderId);
        Payment payment = Payment.builder().id(res.id()).build();

        StoreSettlement storeSettlement = StoreSettlement
                .builder()
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .commission(500)
                .profit(6000)
                .deliveryFee(3000)
                .valueAddedTax(1000)
                .payment(payment)
                .build();

        storeSettlementRepository.save(storeSettlement);
    }


    @Test
    @DisplayName("Success : should fetch same as saved record")
    @Transactional
    void createStoreSettlement() {
        UUID orderId = UUID.randomUUID();
        PaymentRequest paymentRequest = new PaymentRequest(3L, orderId);
        paymentService.createPayment(paymentRequest);

        PaymentResponse res = paymentService.getByOrderId(orderId);
        Long paymentId = res.id();

        StoreSettlementRequest storeSettlementRequest = new StoreSettlementRequest(
                8000, 300, 3000, 800, paymentId
        );
        storeSettlementService.createStoreSettlement(storeSettlementRequest);
        StoreSettlement storeSettlement = storeSettlementRepository.findByPaymentIdAndDeletedFalse(paymentId)
                .orElseThrow(StoreSettlementNotFoundException::new);

        assertNotNull(storeSettlement);
        assertEquals(8000, storeSettlement.getProfit());
        assertFalse(storeSettlement.isDeleted());
        assertEquals(paymentId, storeSettlement.getPayment().getId());
    }

    @Nested
    @Transactional
    class getById {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        void success() {
            List<StoreSettlement> storeSettlement = storeSettlementRepository.findAll();
            int lastIndex = storeSettlement.size()-1;
            UUID savedId = storeSettlement.get(lastIndex).getId();
            StoreSettlementResponse res = storeSettlementService.getById(savedId);

            assertNotNull(res);
            assertEquals(6000, res.profit());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        void fail() {
            assertThrows(StoreSettlementNotFoundException.class,
                    () -> storeSettlementService.getById(UUID.randomUUID()));
        }
    }

    @Nested
    @Transactional
    class getByPaymentId {
        @Test
        @DisplayName("Success : should fetch same as saved record")
        void success() {
            List<StoreSettlement> storeSettlement = storeSettlementRepository.findAll();
            int lastIndex = storeSettlement.size()-1;
            Long savedPaymentId = storeSettlement.get(lastIndex).getPayment().getId();
            StoreSettlementResponse res = storeSettlementService.getByPaymentId(savedPaymentId);

            assertNotNull(res);
            assertEquals(6000, res.profit());
        }
        @Test
        @DisplayName("Fail : should throw an error when payment id doesn't exist")
        void fail() {
            assertThrows(StoreSettlementNotFoundException.class,
                    () -> storeSettlementService.getByPaymentId(300L));
        }
    }

    @Nested
    @Transactional
    class deleteById {
        @Test
        @DisplayName("Success : should change deleted true")
        void success() {
            List<StoreSettlement> storeSettlements = storeSettlementRepository.findAll();
            int lastIndex = storeSettlements.size()-1;
            UUID savedId = storeSettlements.get(lastIndex).getId();

            storeSettlementService.deleteById(savedId);

            StoreSettlement storeSettlement = storeSettlementRepository.findById(savedId)
                    .orElseThrow(StoreSettlementNotFoundException::new);

            assertNotNull(storeSettlement);
            assertTrue(storeSettlement.isDeleted());
        }
        @Test
        @DisplayName("Fail : should throw an error when id doesn't exist")
        void fail() {
            assertThrows(StoreSettlementNotFoundException.class,
                    () -> storeSettlementService.deleteById(UUID.randomUUID()));
        }
    }
    @Nested
    @Transactional
    class deleteByPaymentId {
        @Test
        @DisplayName("Success : should change deleted true")
        void success() {
            List<StoreSettlement> storeSettlements = storeSettlementRepository.findAll();
            int lastIndex = storeSettlements.size()-1;
            Long savedPaymentId = storeSettlements.get(lastIndex).getPayment().getId();
            UUID savedId = storeSettlements.get(lastIndex).getId();

            storeSettlementService.deleteByPaymentId(savedPaymentId);

            StoreSettlement storeSettlement = storeSettlementRepository.findById(savedId)
                    .orElseThrow(StoreSettlementNotFoundException::new);

            assertNotNull(storeSettlement);
            assertTrue(storeSettlement.isDeleted());
        }
        @Test
        @DisplayName("Fail : should throw an error when payment Id doesn't exist")
        void fail() {
            assertThrows(StoreSettlementNotFoundException.class,
                    () -> storeSettlementService.deleteByPaymentId(200L));
        }
    }


    @Test
    @DisplayName("Success : should calculate money properly")
    @Transactional
    void calculateMoneyForStore() {
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

        StoreCalculationResponse res = storeSettlementService.calculateMoneyForStore(orderInfo);

        assertEquals(750, res.commission());
        assertEquals(1363, res.valueAddedTax());
        assertEquals(3900, res.deliveryFee());
        assertEquals(8987, res.profit());
    }
}