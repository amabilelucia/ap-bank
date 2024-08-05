package com.apbank.apbank.services;

import com.apbank.apbank.controller.RefundClient;
import com.apbank.apbank.dto.RefundDTO;
import com.apbank.apbank.dto.RefundResponse;
import com.apbank.apbank.exceptions.RefundException;
import com.apbank.apbank.model.Account;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaRefundConsumer {
    TransactionService transactionService;
    AccountService accountService;
    RefundClient refundClient;

    @KafkaListener(topics = "refund-topic", groupId = "local")
    void refundListener(RefundDTO message) {
        Account account = accountService.findAccountById(message.getAccount());
        RefundResponse response = refundClient.postRefund(message);
        if (Objects.nonNull(response.getMessage())){
            transactionService.saveTransaction(message.getTransactionDTO(), account);
        return;
        }
        throw new RefundException("Falha ao enviar refund");
    }
}
