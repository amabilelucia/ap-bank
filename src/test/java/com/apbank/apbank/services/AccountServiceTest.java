package com.apbank.apbank.services;

import com.apbank.apbank.dto.AccountDTO;
import com.apbank.apbank.enuns.AccountType;
import com.apbank.apbank.exceptions.AccountException;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.model.Account;
import com.apbank.apbank.model.Client;
import com.apbank.apbank.repositories.AccountRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountServiceTest {
    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @Mock
    ClientService clientService;

    @Mock
    Client client;

    @Mock
    Account account;

    AccountDTO accountDTO;

    @BeforeEach
    void setup() {
        openMocks(this);
        accountDTO = new AccountDTO(AccountType.DEBIT, 1L);
    }

    @Test
    void create_whenNewAccount_shouldCreateAccount() {
        when(clientService.findClientById(1L)).thenReturn(client);
        when(client.isActive()).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        accountService.create(accountDTO);

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void create_whenClientNotFound_shouldThrowException() {
        when(clientService.findClientById(accountDTO.idClient())).thenThrow(ClientException.class);
        assertThrows(AccountException.class, () -> accountService.create(accountDTO));
    }

    @Test
    void create_whenTypeAccountExists_shouldThrowException() {
        when(clientService.findClientById(accountDTO.idClient())).thenReturn(client);
        when(client.isActive()).thenReturn(true);
        when(accountRepository.findByAccountTypeAndClient(accountDTO.accountType(), client))
                .thenReturn(Optional.of(account));
        assertThrows(AccountException.class, () -> accountService.create(accountDTO));
    }

    @Test
    void create_whenClientIsNotActive_shouldThrowException() {
        when(clientService.findClientById(accountDTO.idClient())).thenReturn(client);
        when(client.isActive()).thenReturn(false);
        assertThrows(AccountException.class, () -> accountService.create(accountDTO));
    }
}