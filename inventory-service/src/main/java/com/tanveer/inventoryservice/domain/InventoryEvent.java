package com.tanveer.inventoryservice.domain;

import com.tanveer.commonlib.domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record InventoryEvent(UUID aggregateId, UUID correlationId, String sku, int quantity, EventType eventType,
                             Instant occurredAt) implements DomainEvent {

    private static final String AGGREGATE_TYPE = "Inventory";

    @Override
    public String getEventType() {
        return eventType.value();
    }

    @Override
    public Instant getOccurredAt() {
        return occurredAt;
    }

    @Override
    public UUID getAggregateId() {
        return aggregateId;
    }

    @Override
    public String getAggregateType() {
        return AGGREGATE_TYPE;
    }

    public String getSku() {
        return sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

}
