package com.example.waguwagu_payment.kafka.dto;

public record KafkaStatus<T>(
        T data, String status
) {

}
