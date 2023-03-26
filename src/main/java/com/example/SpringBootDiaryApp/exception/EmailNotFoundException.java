package com.example.SpringBootDiaryApp.exception;

public class EmailNotFoundException extends BusinessLogicException{
    public EmailNotFoundException(String message) {
        super(message);
    }
}
