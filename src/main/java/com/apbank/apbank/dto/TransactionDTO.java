package com.apbank.apbank.dto;

import com.apbank.apbank.enuns.TransactionType;
import lombok.NonNull;

import java.math.BigDecimal;

public record TransactionDTO(
        @NonNull BigDecimal amount,
        @NonNull TransactionType transactionType,
        @NonNull Long account,

        Long originalTransaction) {
}
