package com.apbank.apbank.model;

import com.apbank.apbank.enuns.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idTransaction;

    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "id_account")
    Account account;

    Long originalTransaction;
}
