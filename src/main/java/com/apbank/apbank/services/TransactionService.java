package com.apbank.apbank.services;

import com.apbank.apbank.dto.TransactionDTO;
import com.apbank.apbank.exceptions.TransactionException;
import com.apbank.apbank.model.Account;
import com.apbank.apbank.model.Transaction;
import com.apbank.apbank.repositories.TransactionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionService {
    TransactionRepository transactionRepository;

    public void saveTransaction(TransactionDTO transactionDTO, Account account) {
        transactionRepository.save(Transaction.builder()
                .transactionType(transactionDTO.transactionType())
                .account(account)
                .amount(transactionDTO.amount())
                .originalTransaction(transactionDTO.originalTransaction())
                .build());
    }

    public Transaction findTransactionById(Long idTransaction) {
        return transactionRepository.findById(idTransaction)
                .orElseThrow(() -> new TransactionException("Transação não encontrada"));
    }

    public List<Transaction> findByOriginalTransaction(Long originalId) {
        return transactionRepository.findAllByOriginalTransaction(originalId);
    }
}
