package com.tanveer.inventoryservice.infrastructure.messaging;

public interface EventConsumer {
    void consume(String message);
}
