package com.tanveer.inventoryservice.infrastructure.dto;

import com.tanveer.inventoryservice.domain.ConsumeEventType;

import java.time.Instant;

public record ProductEventDto(String aggregateId, String sku, int quantity, Instant occurredAt,
                              ConsumeEventType eventType, String aggregateType) {
}
