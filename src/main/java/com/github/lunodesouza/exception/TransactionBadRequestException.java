package com.github.lunodesouza.exception;

public class TransactionBadRequestException extends RuntimeException {
    public TransactionBadRequestException(String message){
        super(message);
    }
}
