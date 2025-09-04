package com.tanveer.inventoryservice.infrastructure.dto;

import com.tanveer.inventoryservice.domain.EventType;

import java.time.Instant;

public record ProductEventDto(String aggregateId, String sku, int quantity, Instant occurredAt,
                              EventType eventType, String aggregateType) {
}
