package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.domain.EventType;
import com.tanveer.inventoryservice.infrastructure.dto.DomainEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface EventHandler<T extends DomainEventDto> {
    EventType getSupportedEventType();
    void handler(T event) throws InventoryException;
}
