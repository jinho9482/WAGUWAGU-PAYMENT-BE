package com.example.waguwagu_payment.kafka;

import com.example.waguwagu_payment.kafka.dto.KafkaStatus;
import com.example.waguwagu_payment.kafka.dto.RiderIncome;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryIncomeProducer {
    private final KafkaTemplate<String, KafkaStatus<RiderIncome>> kafkaTemplate;

    @Value("${kafka.delivery-topic.name}")
    private String TOPIC;
    @Bean
    private NewTopic newTopicForDeliveryIncome() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    public void send(RiderIncome riderIncome, String status) {
        KafkaStatus<RiderIncome> kafkaStatus = new KafkaStatus<>(riderIncome, status);
        kafkaTemplate.send(TOPIC, kafkaStatus);
    }
}