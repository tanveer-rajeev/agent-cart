package com.tanveer.inventoryservice.infrustructure.dto;

import com.tanveer.inventoryservice.domain.EventType;

import java.time.Instant;
import java.util.UUID;

public record ProductCreatedEventDto(UUID aggregateId, String sku, int quantity, Instant occurredAt, EventType eventType,
                                     String aggregateType) {
}
