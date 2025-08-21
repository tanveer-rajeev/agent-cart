package com.tanveer.inventoryservice.infrustructure.messaging;

import com.tanveer.inventoryservice.domain.EventType;

import java.time.Instant;
import java.util.UUID;

public record ProductCreatedEvent(UUID productId, String sku, int quantity, Instant occurredAt, EventType type,
                                  String aggregateType, UUID aggregateId) {
}
