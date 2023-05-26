package com.example.SpringBootDiaryApp.exception;

public class NotFoundException extends BusinessLogicException{
    public NotFoundException(String message) {
        super(message);
    }
}
