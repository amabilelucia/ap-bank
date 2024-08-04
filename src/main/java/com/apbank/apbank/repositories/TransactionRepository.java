package com.apbank.apbank.repositories;

import com.apbank.apbank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByOriginalTransaction(Long id);
}
