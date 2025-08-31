package com.tanveer.inventoryservice.infrastructure.messaging;

public interface EventConsumer {
    public void consume(String message);
}
