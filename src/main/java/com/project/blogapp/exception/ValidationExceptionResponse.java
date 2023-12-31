package com.project.blogapp.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidationExceptionResponse extends ExceptionResponse {

    private Map<String, String> errors;

    public ValidationExceptionResponse(String path, String message, HttpStatus status, LocalDateTime timestamp, Map<String, String> errors) {
        super(path, message, status, timestamp);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
