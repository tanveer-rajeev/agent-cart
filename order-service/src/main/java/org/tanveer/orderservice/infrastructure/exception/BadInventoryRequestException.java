package org.tanveer.orderservice.infrastructure.exception;

public class BadInventoryRequestException extends Exception {
    public BadInventoryRequestException(String message) {
        super(message);
    }
}
