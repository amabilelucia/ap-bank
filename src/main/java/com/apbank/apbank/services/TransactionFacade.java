package com.apbank.apbank.services;

import com.apbank.apbank.dto.RefundDTO;
import com.apbank.apbank.dto.TransactionDTO;
import com.apbank.apbank.enuns.TransactionType;
import com.apbank.apbank.exceptions.AccountException;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.exceptions.TransactionException;
import com.apbank.apbank.model.Account;
import com.apbank.apbank.model.Client;
import com.apbank.apbank.model.Transaction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class TransactionFacade {
    ClientService clientService;
    AccountService accountService;
    TransactionService transactionService;
    KafkaRefundProducer kafkaRefundProducer;

    public void executeTransaction(TransactionDTO transactionDTO) {
        try {
            Account account = accountService.findAccountById(transactionDTO.account());
            if (account.isActive()) {
                Client client = clientService.findClientById(account.getClient().getIdClient());
                if (client.isActive()) {
                    transaction(account, transactionDTO);
                    return;
                }
                log.info("Cliente encontrado, mas não está ativo.");
                throw new ClientException("Cliente não está ativo.");
            }
            log.info("Conta encontrada, mas não está ativa.");
            throw new AccountException("Conta não está ativa.");
        } catch (AccountException ae) {
            log.warn("Conta não encontrada");
            throw ae;
        }
    }

    void transaction(Account account, TransactionDTO transactionDTO) {
        switch (transactionDTO.transactionType()) {
            case TRANSFER -> transfer(account, transactionDTO);
            case REFUND -> refund(account, transactionDTO);
            case DEPOSIT -> deposit(account, transactionDTO);
        }
    }

    private void deposit(Account account, TransactionDTO transactionDTO) {
        account.setBalance(account.getBalance().add(transactionDTO.amount()));
        updateTransaction(account, transactionDTO);
    }

    void transfer(Account account, TransactionDTO transactionDTO) {
        if (account.getBalance().compareTo(transactionDTO.amount()) >= 0) {
            account.setBalance(account.getBalance().subtract(transactionDTO.amount()));
            updateTransaction(account, transactionDTO);
            return;
        }
        throw new TransactionException("Valor insuficiente");
    }

    void
    refund(Account account, TransactionDTO transactionDTO) {
        if (Objects.nonNull(transactionDTO.originalTransaction())) {
            if (verifyRefundValue(transactionDTO)) {
                account.setBalance(account.getBalance().add(transactionDTO.amount()));
                accountService.update(account);
                kafkaRefundProducer.sendRefund(RefundDTO.builder()
                        .transactionDTO(transactionDTO)
                        .account(account.getIdAccount())
                        .build());
                return;
            }
            throw new TransactionException("Os cancelamentos ultrapassam o valor da transação original");
        }
        throw new TransactionException("Cancelamento sem dados da transação original");
    }

    private boolean verifyRefundValue(TransactionDTO transaction) {
        Transaction originalTransaction = transactionService.findTransactionById(transaction.originalTransaction());
        var transactions = transactionService.findByOriginalTransaction(originalTransaction.getIdTransaction());
        final var refundedValue = transactions.stream().filter(transactionsOfOriginal ->
                        transactionsOfOriginal.getTransactionType().equals(TransactionType.REFUND))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return originalTransaction.getAmount().compareTo(refundedValue.add(transaction.amount())) >= 0;
    }

    void updateTransaction(Account account, TransactionDTO transactionDTO) {
        accountService.update(account);
        transactionService.saveTransaction(transactionDTO, account);
    }
}
