package com.assignment.transaction.exception;


import java.util.List;

public class UserException extends Exception {
    private List<String> validationErrors;

    public UserException(String msg) {
        super(msg);
    }

    public UserException(String message, List<String> validationErrors) {
        super(message);
        this.validationErrors = validationErrors;
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);  // Pass both the message and the cause to the parent constructor
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}

