package com.tanveer.productservice.domain;

import com.tanveer.commonlib.domain.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductEvent(String productId, String sku, String eventType,
                           Instant occurredAt) implements DomainEvent {

  private static final String AGGREGATE_TYPE = "Product";

  @Override
  public String getEventType() {
    return eventType;
  }

  @Override
  public Instant getOccurredAt() {
    return occurredAt;
  }

  @Override
  public String getAggregateId() {
    return productId;
  }

  @Override
  public String getAggregateType() {
    return AGGREGATE_TYPE;
  }

  public String getSku() {
    return sku;
  }

}
