package com.example.waguwagu_payment.kafka;

import com.example.waguwagu_payment.kafka.dto.KafkaStatus;
import com.example.waguwagu_payment.kafka.dto.StoreIncome;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreIncomeProducer {
    private final KafkaTemplate<String, KafkaStatus<StoreIncome>> kafkaTemplate;

    @Value("${kafka.store-topic.name}")
    private String TOPIC;
    @Bean
    private NewTopic newTopicForStoreIncome() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    public void send(StoreIncome storeIncome, String status) {
        KafkaStatus<StoreIncome> kafkaStatus = new KafkaStatus<>(storeIncome, status);
        kafkaTemplate.send(TOPIC, kafkaStatus);
    }
}
