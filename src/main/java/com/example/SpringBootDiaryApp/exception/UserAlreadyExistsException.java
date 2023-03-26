package com.example.SpringBootDiaryApp.exception;

public class UserAlreadyExistsException extends BusinessLogicException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
