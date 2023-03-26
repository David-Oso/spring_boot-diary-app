package com.example.SpringBootDiaryApp.exception;

public class InvalidDetailException extends BusinessLogicException{
    public InvalidDetailException(String message) {
        super(message);
    }
}
