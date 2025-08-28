package com.tanveer.inventoryservice.infrustructure.dto;

import com.tanveer.inventoryservice.domain.ConsumeEventType;
import com.tanveer.inventoryservice.domain.EventType;

import java.time.Instant;
import java.util.UUID;

public record ProductEventDto(String aggregateId, String sku, int quantity, Instant occurredAt,
                              ConsumeEventType eventType, String aggregateType) {
}
