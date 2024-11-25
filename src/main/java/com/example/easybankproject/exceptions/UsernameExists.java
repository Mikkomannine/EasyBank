package com.example.easybankproject.exceptions;

public class UsernameExists extends RuntimeException {
    public UsernameExists(String message) {
        super(message);
    }
}
