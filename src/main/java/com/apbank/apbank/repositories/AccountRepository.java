package com.apbank.apbank.repositories;

import com.apbank.apbank.enuns.AccountType;
import com.apbank.apbank.model.Account;
import com.apbank.apbank.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountTypeAndClient(AccountType accountType, Client client);
}
