package com.example.SpringBootDiaryApp.exception;

public class BusinessLogicException extends RuntimeException{
    public BusinessLogicException(String message) {
        super(message);
    }
}
