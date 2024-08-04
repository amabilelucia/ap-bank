package com.apbank.apbank.services;

import com.apbank.apbank.dto.ClientDTO;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.model.Client;
import com.apbank.apbank.repositories.ClientRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientService {
    ClientRepository clientRepository;

    public void create(ClientDTO clientDTO) {
        Optional<Client> client = clientRepository.findByCpf(clientDTO.cpf());

        if (client.isEmpty()) {
            Client savedClient = clientRepository.save(
                    new Client(clientDTO.cpf(), clientDTO.name(), clientDTO.email(), clientDTO.active()));
            log.info("Cliente criado. IdClient: {}", savedClient.getIdClient());
            return;
        }

        log.warn("Cliente já existe.");
        throw new ClientException("CPF já cadastrado.");
    }

    public Client findClientById(Long idClient) {
        return clientRepository.findById(idClient).orElseThrow(() -> new ClientException("Cliente não encontrado."));
    }
}
