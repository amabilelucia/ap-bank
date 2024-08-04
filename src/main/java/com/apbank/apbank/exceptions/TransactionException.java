package com.apbank.apbank.exceptions;

public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
