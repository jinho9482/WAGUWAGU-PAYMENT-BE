package com.example.waguwagu_payment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STORE_SETTLEMENT")
// 결제가 이루어줘야 정산을 하고 정산 전에는 null 값이기 때문에 따로 payment의 child table로 뺀다.
// 자세한 주문 내역은 결제 id의 주문 id를 참고한다.
public class StoreSettlement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "STORE_SETTLEMENT_ID")
    private UUID id;

    // 정산 시점
    @Column(name = "CREATED_AT")
    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // 중개 수수료
    @Column(name = "COMMISSION")
    private int commission;

    // 가게 정산 금액
    @Column(name = "PROFIT")
    private int profit;

    // 가게 부담 배달비
    @Column(name = "DELIVERY_FEE")
    private int deliveryFee;

    // 부가세
    @Column(name = "VALUE_ADDED_TAX")
    private int valueAddedTax;

    @Column(name = "DELETED")
    @Builder.Default
    @Setter
    private boolean deleted = false;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID")
    private Payment payment;
}
