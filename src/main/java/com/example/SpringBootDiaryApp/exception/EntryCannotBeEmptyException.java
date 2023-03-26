package com.example.SpringBootDiaryApp.exception;

public class EntryCannotBeEmptyException extends BusinessLogicException{
    public EntryCannotBeEmptyException(String message) {
        super(message);
    }
}
