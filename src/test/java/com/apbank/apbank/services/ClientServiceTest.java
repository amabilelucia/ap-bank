package com.apbank.apbank.services;

import com.apbank.apbank.dto.ClientDTO;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.model.Client;
import com.apbank.apbank.repositories.ClientRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@FieldDefaults(level = AccessLevel.PRIVATE)
class ClientServiceTest {
    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    Client client;

    ClientDTO clientDTO;

    @BeforeEach
    void setup() {
        openMocks(this);
        clientDTO = new ClientDTO("123456789", "Test", "test@test.com");
    }

    @Test
    void create_whenNewClient_shouldCreateClient() {
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        clientService.create(clientDTO);

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void create_whenClientExists_shouldThrowException() {
        when(clientRepository.findByCpf(clientDTO.cpf())).thenReturn(Optional.of(client));

        assertThrows(ClientException.class, () -> clientService.create(clientDTO));
    }
}