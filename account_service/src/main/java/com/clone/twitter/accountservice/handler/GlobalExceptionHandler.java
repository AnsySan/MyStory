package com.clone.twitter.accountservice.handler;

import com.clone.twitter.accountservice.exception.DataValidationException;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleInvalidFieldException(EntityNotFoundException ex) {
        log.error("Exception caused: {}. \n" +
                "Stacktrace: {}", ex.getMessage(), ex.getStackTrace());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(DataValidationException.class)
    public ResponseEntity<Object> handleInvalidFieldException(DataValidationException ex) {
        log.error("Exception caused: {}. \n" +
                "Stacktrace: {}", ex.getMessage(), ex.getStackTrace());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleInvalidFieldException(FeignException ex) {
        log.error("Exception caused: {}. \n" +
                "Stacktrace: {}", ex.getMessage(), ex.getStackTrace());

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleException(MethodArgumentNotValidException e) {
        var bindingResult = e.getBindingResult();
        Map<String, String> fieldErrors = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(error -> {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            });
        }
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(fieldErrors);
    }
}