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
@Table(name = "RIDER_SETTLEMENT")
// 결제가 이루어줘야 정산을 하고 정산 전에는 null 값이기 때문에 따로 payment의 child table로 뺀다.
// 자세한 주문 내역은 결제 id의 주문 id를 참고한다.
public class RiderSettlement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "RIDER_SETTLEMENT_ID")
    private UUID id;

    // 정산 시점
    @Column(name = "CREATED_AT")
    @Builder.Default
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    // 라이더 정산 금액
    @Column(name = "PROFIT")
    private int profit;

    // 라이더 소득세
    @Column(name = "INCOME_TAX")
    private int incomeTax;

    // 라이더 주민세
    @Column(name = "RESIDENT_TAX")
    private int residentTax;

    @Column(name = "DELETED")
    @Builder.Default
    @Setter
    private boolean deleted = false;

    @OneToOne
    @JoinColumn(name = "PAYMENT_ID", unique = true)
    private Payment payment;
}
