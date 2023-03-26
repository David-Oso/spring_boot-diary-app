package com.example.SpringBootDiaryApp.exception;

public class UserNotFoundException extends BusinessLogicException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
