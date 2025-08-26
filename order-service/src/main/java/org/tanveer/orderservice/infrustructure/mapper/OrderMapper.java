package org.tanveer.orderservice.infrustructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.model.OrderEvent;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;
import org.tanveer.orderservice.infrustructure.persistence.OrderEntity;
import org.tanveer.orderservice.infrustructure.persistence.OrderEventEntity;
import org.tanveer.orderservice.infrustructure.persistence.OrderItemEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static OrderEventEntity toEventEntity(OrderEvent event) {
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

    public static OrderEntity toEntity(Order order) {
        return OrderEntity.builder().orderId(UUID.randomUUID())
                .customerId(order.getCustomerId())
                .items(toOrderItemEntityList(order.getItems()))
                .status(order.getStatus()).build();
    }

    public static OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(order.getOrderId(), order.getStatus(), order.calculateTotalAmount());
    }

    public static Order toDomain(OrderEntity orderEntity) {
        return Order.create(orderEntity.getOrderId(), orderEntity.getCustomerId(),
                toOrderItemList(orderEntity.getItems()));
    }

    public static List<OrderItemEntity> toOrderItemEntityList(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderMapper::toOrderItemEntity).toList();
    }

    public static OrderItemEntity toOrderItemEntity(OrderItem orderItem) {
        return OrderItemEntity.builder().productId(orderItem.getProductId())
                .name(orderItem.getName())
                .sku(orderItem.getSku())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    public static List<OrderItem> toOrderItemList(List<OrderItemEntity> orderItems) {
        return orderItems.stream().map(OrderMapper::toOrderItem).toList();
    }

    public static OrderItem toOrderItem(OrderItemEntity orderItemEntity) {
        return new OrderItem(orderItemEntity.getProductId(), orderItemEntity.getName(), orderItemEntity.getSku(),
                orderItemEntity.getPrice(), orderItemEntity.getQuantity());
    }
}
