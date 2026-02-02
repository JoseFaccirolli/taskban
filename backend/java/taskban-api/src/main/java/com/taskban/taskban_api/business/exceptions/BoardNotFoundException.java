package com.taskban.taskban_api.business.exceptions;

public class BoardNotFoundException extends RuntimeException{

    public BoardNotFoundException(String message) {
        super(message);
    }
}
