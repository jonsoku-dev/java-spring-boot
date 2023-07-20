package com.example.validationexample.exception;

public class QueryParamLengthException extends RuntimeException {
    public QueryParamLengthException(String message) {
        super(message);
    }
}
