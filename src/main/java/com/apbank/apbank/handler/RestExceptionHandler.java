package com.apbank.apbank.handler;

import com.apbank.apbank.exceptions.AccountException;
import com.apbank.apbank.exceptions.ClientException;
import com.apbank.apbank.exceptions.DetailsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<DetailsException> clientNotValid(ClientException ce) {
        return new ResponseEntity<>(DetailsException.builder()
                .title("Falha na criação de usuário")
                .status(HttpStatus.CONFLICT.value())
                .message(ce.getMessage())
                .build(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AccountException.class)
    public ResponseEntity<DetailsException> accountNotValid(AccountException ae) {
        return new ResponseEntity<>(DetailsException.builder()
                .title("Falha na criação da conta")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(ae.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
