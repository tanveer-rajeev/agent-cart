package org.tanveer.orderservice.domain.model;

import com.tanveer.commonlib.domain.DomainEvent;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderEvent(String aggregateId, String customerId, String productId, String sku, int quantity,
                         EventType eventType, Instant occurredAt) implements DomainEvent {

    private static final String AGGREGATE_TYPE = "ORDER";

    @Override
    public String getEventType() {
        return eventType.toString();
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
}
