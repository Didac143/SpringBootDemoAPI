package com.example.springbootdemoapi.model.exception;

public class RequestException extends RuntimeException{
    public RequestException(String message) {
        super(message);
    }
}
