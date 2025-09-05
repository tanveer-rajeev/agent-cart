package org.tanveer.orderservice.infrastructure.exception;

public class InventoryServiceUnavailableException extends Exception {
    public InventoryServiceUnavailableException(String message){
        super(message);
    }
}
