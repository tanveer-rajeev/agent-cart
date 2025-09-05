package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface EventConsumer {
    void consume(String message) throws InventoryException, JsonProcessingException;
}
