package com.example.chat_backend.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Authentication error");
    }
    
    public AuthenticationException(String message) {
        super(message);
    }
}
