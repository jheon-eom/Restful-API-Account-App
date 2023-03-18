package com.ejh.accountapp.bank.handler.exception;

public class CustomApiException extends RuntimeException {

    public CustomApiException(String message) {
        super(message);
    }
}
