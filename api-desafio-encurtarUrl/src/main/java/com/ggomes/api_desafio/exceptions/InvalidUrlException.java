package com.ggomes.api_desafio.exceptions;


public class InvalidUrlException extends RuntimeException {
    public InvalidUrlException(String message) {
        super(message);
    }
}