package com.lld.orderingapp.exception;

public class InvalidRestaurantException extends RuntimeException {
    public InvalidRestaurantException(String message) {
        super(message);
    }
}
