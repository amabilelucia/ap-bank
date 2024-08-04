package com.apbank.apbank.controller;

import com.apbank.apbank.dto.AccountDTO;
import com.apbank.apbank.services.AccountService;
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
@RequestMapping("/v1/account")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountController {
    AccountService accountService;

    @PostMapping(value = "/create")
    ResponseEntity<Void> createAccount(@RequestBody AccountDTO accountDTO) {
        accountService.create(accountDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
