package com.tanveer.agent.infrastructure.dto;

import java.time.Instant;

public record OrderEventDto(String aggregateId, String customerId, String productName,String sku, int quantity,
                            EventType eventType, Instant occurredAt) implements DomainEventDto {

}
