package com.example.waguwagu_payment.kafka.dto;

import com.example.waguwagu_payment.domain.type.RiderTransportation;

import java.util.UUID;

public record OrderInfo(
        UUID orderId,
        Long riderId,
        int deliveryFee,
        String storeName,
        Long storeId,
        int foodPrice,
        RiderTransportation riderTransportation,
        double distanceFromStoreToCustomer
) {
    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId=" + orderId +
                ", riderId=" + riderId +
                ", deliveryFee=" + deliveryFee +
                ", storeName='" + storeName + '\'' +
                ", storeId=" + storeId +
                ", foodPrice=" + foodPrice +
                ", riderTransportation=" + riderTransportation +
                ", distanceFromStoreToCustomer=" + distanceFromStoreToCustomer +
                '}';
    }
}
