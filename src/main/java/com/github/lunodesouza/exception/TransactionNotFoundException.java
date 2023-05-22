package com.github.lunodesouza.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message){
        super(message);
    }
}
