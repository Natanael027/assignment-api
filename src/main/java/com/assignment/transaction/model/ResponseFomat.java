package com.assignment.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseFomat<T> {
    private int status;
    private String message;
    private T data;
}
