package com.apbank.apbank.controller;

import com.apbank.apbank.dto.TransactionDTO;
import com.apbank.apbank.services.TransactionFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/transaction")
@AllArgsConstructor
public class TransactionController {
    TransactionFacade transactionFacade;

    @PostMapping
    ResponseEntity<Void> transaction(@RequestBody TransactionDTO transactionDTO) {
        transactionFacade.executeTransaction(transactionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
