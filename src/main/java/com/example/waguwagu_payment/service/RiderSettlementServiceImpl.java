package com.example.waguwagu_payment.service;

import com.example.waguwagu_payment.domain.dto.request.RiderSettlementRequest;
import com.example.waguwagu_payment.domain.dto.response.RiderSettlementResponse;
import com.example.waguwagu_payment.domain.entity.RiderSettlement;
import com.example.waguwagu_payment.domain.type.RiderTransportation;
import com.example.waguwagu_payment.global.dao.RiderSettlementServiceDao;
import com.example.waguwagu_payment.kafka.dto.OrderInfo;
import com.example.waguwagu_payment.kafka.dto.RiderIncome;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RiderSettlementServiceImpl implements RiderSettlementService {

    private final RiderSettlementServiceDao riderSettlementServiceDao;
    private final double INCOME_TAX_RATIO = 0.03;
    private final double RESIDENT_TAX_RATIO = 0.003;

    @Override
    public void createRiderSettlement(RiderSettlementRequest req) {
        riderSettlementServiceDao.save(req);
    }

    @Override
    public RiderSettlementResponse getById(UUID id) {
        RiderSettlement riderSettlement = riderSettlementServiceDao.findById(id);
        RiderSettlementResponse res = RiderSettlementResponse.from(riderSettlement);
        return res;
    }

    @Override
    public RiderSettlementResponse getByPaymentId(UUID paymentId) {
        RiderSettlement riderSettlement = riderSettlementServiceDao.findByPaymentId(paymentId);
        RiderSettlementResponse res = RiderSettlementResponse.from(riderSettlement);
        return res;
    }

    @Override
    public void deleteById(UUID id) {
        RiderSettlement riderSettlement = riderSettlementServiceDao.findById(id);
        riderSettlement.setDeleted(true);
    }

    @Override
    public void deleteByPaymentId(UUID paymentId) {
        RiderSettlement riderSettlement = riderSettlementServiceDao.findByPaymentId(paymentId);
        riderSettlement.setDeleted(true);
    }

// 라이더 한테 보낼 정산 값
// = 배달팁
// + 사장님 부담 배달료 (고객~ 가게 거리 및 날씨 생각해서 주문 접수할 때 직접 입력)
// - 세금 3.3%(소득세 3%, 주민세 0.3%), 보험료는 없다고 가정
    public RiderIncome calculateMoneyForRider(OrderInfo dto) {
        int costByDistance = RiderTransportation
                .calculateDeliveryFeeByTransportation(dto.riderTransportation(), dto.distanceFromStoreToCustomer());
        int totalDeliveryFeeBeforeTax = costByDistance + dto.deliveryFee();
        int incomeTax = (int) (totalDeliveryFeeBeforeTax * INCOME_TAX_RATIO);
        int residentTax = (int) (totalDeliveryFeeBeforeTax * RESIDENT_TAX_RATIO);
        int totalDeliveryFeeAfterTax = totalDeliveryFeeBeforeTax - incomeTax - residentTax;

        RiderIncome riderIncome = new RiderIncome(
                totalDeliveryFeeAfterTax,
                incomeTax,
                residentTax,
                dto.storeName(),
                dto.orderId(),
                new Timestamp(System.currentTimeMillis())
        );
        return riderIncome;
    }
}



