package com.apbank.apbank.services;

import com.apbank.apbank.dto.AccountDTO;
import com.apbank.apbank.exceptions.AccountException;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.model.Account;
import com.apbank.apbank.model.Client;
import com.apbank.apbank.repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log4j2
public class AccountService {
    AccountRepository accountRepository;
    ClientService clientService;

    public void create(AccountDTO accountDTO) {
        try {
            Client client = clientService.findClientById(accountDTO.idClient());
            Optional<Account> account = accountRepository.findByAccountTypeAndClient(accountDTO.accountType(), client);

            if (account.isEmpty()) {
                Account savedAccount = accountRepository.save(new Account(accountDTO.accountType(), client));
                log.info("Conta criada com sucesso. idAccount: {}", savedAccount.getIdAccount());
                return;
            }

            log.warn("Conta com accountType já criada para o Cliente. idClient: {}", client.getIdClient());
            throw new AccountException(String.format("Já existe uma conta %s para o cliente", accountDTO.accountType().name()));

        } catch (ClientException ce) {
            throw new AccountException(ce.getMessage());
        }
    }
}
