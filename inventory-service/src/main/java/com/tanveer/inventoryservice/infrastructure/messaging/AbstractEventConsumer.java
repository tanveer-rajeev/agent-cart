package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.DomainEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEventConsumer<T extends DomainEventDto> {
    private final ObjectMapper objectMapper;
    private final EventHandlerRegistry<T> handlerRegistry;
    private final Class<T> eventClass;

    protected AbstractEventConsumer(ObjectMapper objectMapper, EventHandlerRegistry<T> handlerRegistry,
                                    Class<T> eventClass) {
        this.objectMapper = objectMapper;
        this.handlerRegistry = handlerRegistry;
        this.eventClass = eventClass;
    }

    protected void process(String message) throws JsonProcessingException, InventoryException {
        T event = objectMapper.readValue(message, eventClass);
        EventHandler<T> handler = handlerRegistry.getHandler(event.eventType());

        if (handler == null) {
            log.warn("No handler found for event type {}", event.eventType());
            return;
        }

        handler.handler(event);
    }
}
