package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.PaymentResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.entity.Payment;
import com.example.waguwagu_payment.kafka.DeliveryIncomeProducer;
import com.example.waguwagu_payment.kafka.dto.KafkaStatus;
import com.example.waguwagu_payment.kafka.StoreIncomeProducer;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.kafka.dto.RiderIncome;
import com.example.waguwagu_payment.kafka.dto.StoreIncome;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class KafkaSettlementService {
    private final DeliveryIncomeProducer deliveryIncomeProducer;
    private final StoreIncomeProducer storeIncomeProducer;
    private final RiderSettlementService riderSettlementService;
    private final StoreSettlementService storeSettlementService;
    private final PaymentService paymentService;

    // 배달완료되면 가게와 라이더에게 정산금액을 보내줌
    @KafkaListener(topics = "payment", id = "payment")
    public void sendMoney(KafkaStatus<OrderInfo> dto) {
        log.info(dto.toString());
        if (dto.status().equals("payment")) {
            RiderIncome riderIncome = riderSettlementService.calculateMoneyForRider(dto.data());
            StoreCalculationResponse storeCalculationResponse = storeSettlementService.calculateMoneyForStore(dto.data());
            PaymentResponse paymentResponse = paymentService.getByOrderId(riderIncome.orderId());
            RiderSettlementRequest riderSettlementRequest = new RiderSettlementRequest(
                    riderIncome.profit(),
                    riderIncome.incomeTax(),
                    riderIncome.residentTax(),
                    paymentResponse.id()
            );
            StoreSettlementRequest storeSettlementRequest = new StoreSettlementRequest(
                    storeCalculationResponse.profit(),
                    storeCalculationResponse.commission(),
                    storeCalculationResponse.deliveryFee(),
                    storeCalculationResponse.valueAddedTax(),
                    paymentResponse.id()
            );
            riderSettlementService.createRiderSettlement(riderSettlementRequest); // DB에 라이더 정산 내역 저장
            storeSettlementService.createStoreSettlement(storeSettlementRequest); // DB에 가게 정산 내역 저장

            StoreIncome storeIncome = new StoreIncome(
                    dto.data().storeId(),
                    new Timestamp(System.currentTimeMillis()),
                    storeCalculationResponse.profit()
            );
            deliveryIncomeProducer.send(riderIncome, "insert");  // 라이더에게 정산금액을 보내줌
            storeIncomeProducer.send(storeIncome, "insert"); // 가게에 정산금액을 보내줌
        }
    }
}
