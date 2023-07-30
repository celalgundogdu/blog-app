package com.project.blogapp.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionResponse {

    private String path;
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;

    public ExceptionResponse(String path, String message, HttpStatus status, LocalDateTime timestamp) {
        this.path = path;
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
