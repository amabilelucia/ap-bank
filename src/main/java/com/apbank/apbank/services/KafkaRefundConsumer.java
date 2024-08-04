package com.apbank.apbank.services;

import com.apbank.apbank.dto.RefundDTO;
import com.apbank.apbank.model.Account;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class KafkaRefundConsumer {
    TransactionService transactionService;
    AccountService accountService;

    @KafkaListener(topics = "refund-topic", groupId = "local")
    void refundListener(RefundDTO message) {
        Account account = accountService.findAccountById(message.getAccount());
        transactionService.saveTransaction(message.getTransactionDTO(), account);
    }
}
