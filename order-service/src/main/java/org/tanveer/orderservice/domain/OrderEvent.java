package org.tanveer.orderservice.domain;

import com.tanveer.commonlib.domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record OrderEvent(UUID aggregateId, UUID customerId, UUID productId, EventType eventType,Instant occurredAt)
        implements DomainEvent {

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
    public UUID getAggregateId() {
        return aggregateId;
    }

    @Override
    public String getAggregateType() {
        return AGGREGATE_TYPE;
    }
}
