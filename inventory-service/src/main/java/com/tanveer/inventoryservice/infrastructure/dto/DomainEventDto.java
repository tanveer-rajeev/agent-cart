package com.tanveer.inventoryservice.infrastructure.dto;

import com.tanveer.inventoryservice.domain.EventType;

public interface DomainEventDto {
    EventType eventType();
}
