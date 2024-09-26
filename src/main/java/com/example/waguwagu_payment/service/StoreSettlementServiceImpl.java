package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.StoreSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.StoreCalculationResponse;
import com.example.waguwagu_payment.domain.dto.response.StoreSettlementResponse;
import com.example.waguwagu_payment.domain.entity.StoreSettlement;
import com.example.waguwagu_payment.domain.type.RiderTransportation;
import com.example.waguwagu_payment.global.dao.StoreSettlementServiceDao;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class StoreSettlementServiceImpl implements StoreSettlementService {
    private final StoreSettlementServiceDao storeSettlementServiceDao;
    private final double COMMISSION_RATIO = 0.05;
    private final double VAT_RATIO = (double) 1 / 11;

    @Override
    public void createStoreSettlement(StoreSettlementRequest req) {
        storeSettlementServiceDao.save(req);
    }

    @Override
    public StoreSettlementResponse getById(UUID id) {
        StoreSettlement storeSettlement = storeSettlementServiceDao.findById(id);
        StoreSettlementResponse res = StoreSettlementResponse.from(storeSettlement);
        return res;
    }

    @Override
    public StoreSettlementResponse getByPaymentId(Long paymentId) {
        StoreSettlement storeSettlement = storeSettlementServiceDao.findByPaymentId(paymentId);
        StoreSettlementResponse res = StoreSettlementResponse.from(storeSettlement);
        return res;
    }

    @Override
    public void deleteById(UUID id) {
        StoreSettlement storeSettlement = storeSettlementServiceDao.findById(id);
        storeSettlement.setDeleted(true);
    }

    @Override
    public void deleteByPaymentId(Long paymentId) {
        StoreSettlement storeSettlement = storeSettlementServiceDao.findByPaymentId(paymentId);
        storeSettlement.setDeleted(true);
    }

//    public StoreIncome calculateMoneyForStore(OrderInfo dto) {
//        int costByTransportation = RiderTransportation
//                .calculateDeliveryFeeByTransportation(dto.riderTransportation(), dto.distanceFromStoreToCustomer());
//        int commission = (int) (dto.foodPrice() * COMMISSION_RATIO);
//        int vat = (int) (dto.foodPrice() * VAT_RATIO);
//        int totalIncomeAfterTax = dto.foodPrice() - commission - costByTransportation - vat;
//
//        StoreIncome storeIncome =
//                new StoreIncome(dto.storeId(),
//                        new Timestamp(System.currentTimeMillis()),
//                        totalIncomeAfterTax
//                );
//        return storeIncome;
//    }

    // 가게 정산액
    // = 판매 금액
    // - 중개 수수료 (5%)
    // - 사장님 부담 배달 이용료 (고객~가게 거리 및 이동 수단에 따라 차등 적용)
    // - 혜택 쿠폰 - 부가세 (음식 가격의 10/11)
    // - 결제 정산 수수료 (없다고 가정)
    public StoreCalculationResponse calculateMoneyForStore(OrderInfo dto) {
        int costByTransportation = RiderTransportation
                .calculateDeliveryFeeByTransportation(dto.riderTransportation(), dto.distanceFromStoreToCustomer());
        int commission = (int) (dto.foodPrice() * COMMISSION_RATIO);
        int vat = (int) (dto.foodPrice() * VAT_RATIO);
        int profit = dto.foodPrice() - commission - costByTransportation - vat;

        StoreCalculationResponse res =
                new StoreCalculationResponse (
                        profit, commission, costByTransportation, vat
                );
        return res;
    }
}
