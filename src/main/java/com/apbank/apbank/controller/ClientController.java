package com.apbank.apbank.controller;

import com.apbank.apbank.dto.ClientDTO;
import com.apbank.apbank.services.ClientService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/client")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientController {
    ClientService clientService;

    @PostMapping(value = "/create")
    ResponseEntity<String> createClient(@RequestBody ClientDTO clientDTO) {
        clientService.create(clientDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
