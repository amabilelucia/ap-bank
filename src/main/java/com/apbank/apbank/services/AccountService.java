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
            if (!client.isActive()) {
                throw new AccountException("Não é possível criar uma conta, pois o cliente não está ativo");
            }

            Optional<Account> account = accountRepository.findByAccountTypeAndClient(accountDTO.accountType(), client);

            if (account.isEmpty()) {
                Account savedAccount = accountRepository.save(new Account(accountDTO.accountType(), client, accountDTO.active()));
                log.info("Conta criada com sucesso. idAccount: {}", savedAccount.getIdAccount());
                return;
            }

            log.warn("Conta com accountType já criada para o Cliente. idClient: {}", client.getIdClient());
            throw new AccountException(String.format("Já existe uma conta %s para o cliente", accountDTO.accountType().name()));

        } catch (ClientException ce) {
            throw new AccountException(ce.getMessage());
        }
    }

    public Account findAccountById(Long idAccount) {
        return accountRepository.findById(idAccount).orElseThrow(() -> new AccountException("Conta não encontrada"));
    }

    public void update(Account account) {
        accountRepository.save(account);
    }
}
