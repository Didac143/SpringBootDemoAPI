package com.example.springbootdemoapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ApiException> handleApiException(RequestException e) {
        return new ResponseEntity<>(new ApiException(
                e.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST);
    }

}
