package com.apbank.apbank.model;

import com.apbank.apbank.enuns.AccountType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idAccount;

    @Enumerated(EnumType.STRING)
    AccountType accountType;

    @ManyToOne
    @JoinColumn(name = "id_client")
    Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions;

    public Account(AccountType accountType, Client client) {
        this.accountType = accountType;
        this.client = client;
        this.transactions = null;
    }
}
