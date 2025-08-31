package com.tanveer.productservice.infrastructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tanveer.productservice.infrastructure.persistence.ProductEventEntity;
import com.tanveer.productservice.domain.ProductEvent;

import java.util.HashMap;
import java.util.Map;

public class ProductEventMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


  public static ProductEventEntity toEntity(ProductEvent event) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("aggregateType", event.getAggregateType());
    payloadMap.put("aggregateId", event.getAggregateId());
    payloadMap.put("eventType", event.getEventType());
    payloadMap.put("sku", event.getSku());
    payloadMap.put("occurredAt", event.getOccurredAt() != null ? event.getOccurredAt().toString() : null);
    try {
      return ProductEventEntity.builder()
        .aggregateType(event.getAggregateType())
        .aggregateId(event.getAggregateId())
        .eventType(event.getEventType())
        .payload(objectMapper.writeValueAsString(payloadMap))
        .published(false)
        .occurredAt(event.getOccurredAt())
        .sku(event.getSku())
        .build();
    } catch (Exception e) {
      throw new RuntimeException("Failed to serialize event", e);
    }
  }

  public static ProductEvent toDomain(ProductEventEntity entity) {
    try {
      return objectMapper.readValue(entity.getPayload(), ProductEvent.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to deserialize event", e);
    }
  }
}
