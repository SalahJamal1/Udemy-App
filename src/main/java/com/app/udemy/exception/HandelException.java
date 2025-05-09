package com.app.udemy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class HandelException {
    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handelException(RuntimeException exc) {
        AppErrorResponse error = AppErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .timestamp(Timestamp.from(Instant.now())).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value())
                .body(error);
    }

    @ExceptionHandler
    private ResponseEntity<AppErrorResponse> handelException(Exception exc) {
        AppErrorResponse error = AppErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timestamp(Timestamp.from(Instant.now())).build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(error);
    }
}
