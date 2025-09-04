package com.tanveer.inventoryservice.infrastructure.dto;

import com.tanveer.inventoryservice.domain.EventType;

import java.time.Instant;

public record OrderEventDto(String aggregateId, String customerId, String productId, String sku, int quantity,
                            EventType eventType, Instant occurredAt) {
}
