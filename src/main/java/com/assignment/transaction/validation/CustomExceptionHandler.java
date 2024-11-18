package com.assignment.transaction.validation;


import com.assignment.transaction.exception.UserException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@ControllerAdvice
public class CustomExceptionHandler {


    @ExceptionHandler(UserException.class)
    public  ResponseEntity<Object> handleGlobalException(UserException ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 102);

        if (ex.getValidationErrors() != null && !ex.getValidationErrors().isEmpty()) {
            responseBody.put("message", ex.getValidationErrors());
        }
        responseBody.put("data", null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleJWTExp(Exception ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 108);
        responseBody.put("message", "Token tidak tidak valid atau kadaluwarsa");
        responseBody.put("data", null);

        return new ResponseEntity<>(responseBody, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", 102);
        responseBody.put("message", ex.getMessage());
        responseBody.put("data", null);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }
}