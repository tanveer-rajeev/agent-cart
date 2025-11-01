package com.tanveer.agent.infrastructure.dto;

import java.time.Instant;

public record InventoryEventDto(String name, String description, double price,
                                String sku, int quantity, Instant occurredAt, EventType eventType, String aggregateType)
        implements DomainEventDto {
}
