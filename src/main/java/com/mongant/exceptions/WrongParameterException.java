package com.mongant.exceptions;

public class WrongParameterException extends RuntimeException{

    public WrongParameterException(String message) {
        super(message);
    }
}
