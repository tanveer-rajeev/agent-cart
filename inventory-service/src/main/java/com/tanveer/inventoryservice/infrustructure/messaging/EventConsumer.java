package com.tanveer.inventoryservice.infrustructure.messaging;

public interface EventConsumer {
    public void consume(String message);
}
