package org.tanveer.orderservice.infrustructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.tanveer.orderservice.domain.model.OrderEvent;
import org.tanveer.orderservice.infrustructure.persistence.OrderEventEntity;

import java.util.HashMap;
import java.util.Map;

public class OrderMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static OrderEventEntity toEntity(OrderEvent event) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("aggregateType", event.getAggregateType());
        payloadMap.put("customerId", event.customerId());
        payloadMap.put("productId", event.productId());
        payloadMap.put("aggregateId", event.getAggregateId());
        payloadMap.put("eventType", event.getEventType());
        payloadMap.put("occurredAt", event.getOccurredAt() != null ? event.getOccurredAt().toString() : null);

        try {
            return OrderEventEntity.builder()
                    .aggregateId(event.getAggregateId())
                    .customerId(event.customerId())
                    .productId(event.productId())
                    .eventType(event.getEventType())
                    .occurredAt(event.getOccurredAt())
                    .payload(objectMapper.writeValueAsString(payloadMap))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }

    }

}
