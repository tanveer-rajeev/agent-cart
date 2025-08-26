package com.tanveer.inventoryservice.domain;

import com.tanveer.commonlib.domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record InventoryEvent(String aggregateId, String productId, String sku, int quantity, EventType eventType,
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
    public String getAggregateId() {
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

    public String getProductId() {
        return productId;
    }

}
