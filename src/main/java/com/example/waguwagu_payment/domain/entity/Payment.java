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
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "PAYMENT_ID")
    private UUID id;

    @Column(name = "ORDER_ID", unique = true)
    private UUID orderId;

    // 결제 시점
    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @Column(name = "CANCELED")
    @Builder.Default
    @Setter
    private boolean canceled = false;
}
