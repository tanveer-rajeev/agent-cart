package com.tanveer.inventoryservice.infrustructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.infrustructure.persistence.InventoryEventEntity;

import java.util.HashMap;
import java.util.Map;

public class InventoryEventMapper {

  private static final ObjectMapper objectMapper = new ObjectMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  public static InventoryEventEntity toEntity(InventoryEvent event) {
    Map<String, Object> payloadMap = new HashMap<>();
    payloadMap.put("aggregateType", event.getAggregateType());
    payloadMap.put("aggregateId", event.getAggregateId());
    payloadMap.put("eventType", event.getEventType());
    payloadMap.put("sku", event.getSku());
    payloadMap.put("occurredAt", event.getOccurredAt() != null ? event.getOccurredAt().toString() : null);

    try {
      return InventoryEventEntity.builder()
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

  public static InventoryEvent toDomain(InventoryEventEntity entity) {
    try {
      return objectMapper.readValue(entity.getPayload(), InventoryEvent.class);
    } catch (Exception e) {
      throw new RuntimeException("Failed to deserialize event", e);
    }
  }
}
