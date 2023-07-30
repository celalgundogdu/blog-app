package com.project.blogapp.exception;

import com.project.blogapp.constant.Messages;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleEntityNotFoundException(EntityNotFoundException exception,
                                                           HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleEntityAlreadyExistsException(EntityAlreadyExistsException exception,
                                                                HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                   HttpServletRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ValidationExceptionResponse(
                request.getServletPath(),
                Messages.Exception.VALIDATION,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now(),
                validationErrors
        );
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ExceptionResponse handleBusinessException(BusinessException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.UNPROCESSABLE_ENTITY,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<String> handleRequestNotPermittedException(RequestNotPermitted exception) {
        return new ResponseEntity<>("Request limit exceeded", HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                       HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleDataIntegrityViolationException(ConstraintViolationException exception,
                                                                   HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                   HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                LocalDateTime.now()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleException(RuntimeException exception, HttpServletRequest request) {
        return new ExceptionResponse(
                request.getServletPath(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                LocalDateTime.now()
        );
    }
}
