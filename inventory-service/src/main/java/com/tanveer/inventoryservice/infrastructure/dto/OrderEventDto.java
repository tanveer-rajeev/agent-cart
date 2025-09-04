package com.tanveer.inventoryservice.infrastructure.dto;

import com.tanveer.inventoryservice.domain.ConsumeEventType;

import java.time.Instant;

public record OrderEventDto(String aggregateId, String customerId, String productId, String sku, int quantity,
                            Instant occurredAt, ConsumeEventType eventType) {
}
