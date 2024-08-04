package com.apbank.apbank.services;

import com.apbank.apbank.dto.RefundDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class KafkaRefundProducer {
    KafkaTemplate<String, RefundDTO> kafkaTemplate;

    Environment env;

    public void sendRefund(RefundDTO refundDTO) {
        kafkaTemplate.send(Objects.requireNonNull(env.getProperty("spring.kafka.topic.refund")), refundDTO);
    }
}
