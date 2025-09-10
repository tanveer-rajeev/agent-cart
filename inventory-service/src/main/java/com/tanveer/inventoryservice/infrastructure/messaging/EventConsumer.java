package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import org.springframework.kafka.support.Acknowledgment;

public interface EventConsumer {
    void consume(String message, Acknowledgment ack) throws InventoryException, JsonProcessingException;
}
