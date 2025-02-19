package com.example.bookstore.exception;

public class InsufficientLoyaltyPointsException extends RuntimeException {
    public InsufficientLoyaltyPointsException(String message) {
        super(message);
    }
}
