package org.tanveer.orderservice.infrastructure.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityDto;
import org.tanveer.orderservice.infrastructure.dto.OrderRequestDto;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.model.OrderEvent;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.infrastructure.dto.OrderResponseDto;
import org.tanveer.orderservice.infrastructure.persistence.OrderEntity;
import org.tanveer.orderservice.infrastructure.persistence.OrderEventEntity;
import org.tanveer.orderservice.infrastructure.persistence.OrderItemEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static OrderEventEntity toEventEntity(OrderEvent event) {
        Map<String, Object> payloadMap = new HashMap<>();
        payloadMap.put("aggregateType", event.getAggregateType());
        payloadMap.put("customerId", event.customerId());
        payloadMap.put("productId", event.productId());
        payloadMap.put("sku", event.sku());
        payloadMap.put("quantity", event.quantity());
        payloadMap.put("aggregateId", event.getAggregateId());
        payloadMap.put("eventType", event.getEventType());
        payloadMap.put("occurredAt", event.getOccurredAt() != null ? event.getOccurredAt().toString() : null);

        try {
            return OrderEventEntity.builder()
                    .aggregateType(event.getAggregateType())
                    .aggregateId(event.getAggregateId())
                    .customerId(event.customerId())
                    .productId(event.productId())
                    .sku(event.sku())
                    .quantity(event.quantity())
                    .eventType(event.getEventType())
                    .occurredAt(event.getOccurredAt())
                    .payload(objectMapper.writeValueAsString(payloadMap))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize event", e);
        }

    }

    public static OrderEntity domainToEntity(Order order) {
        return OrderEntity.builder().orderId(order.getOrderId())
                .customerId(order.getCustomerId())
                .items(toOrderItemEntityList(order.getItems()))
                .status(order.getStatus()).build();
    }

    public static Order dtoToDomain(OrderRequestDto requestDto) {
        return Order.create(requestDto.getCustomerId(), requestDto.getItems()
                .stream().map(OrderMapper::toOrderItem).toList());
    }

    public static OrderItem toOrderItem(OrderRequestDto.OrderItemDto orderItemDto) {
        return new OrderItem(orderItemDto.getId(), orderItemDto.getProductId(), orderItemDto.getName(),
                orderItemDto.getSku(), orderItemDto.getPrice(), orderItemDto.getQuantity());
    }

    public static OrderResponseDto domainToResponseDto(Order order, List<ItemAvailabilityDto> availabilityDto) {
        return new OrderResponseDto(order.getOrderId(), order.getStatus(), order.calculateTotalAmount(),
                availabilityDto);
    }

    public static Order entityToDomain(OrderEntity orderEntity) {
        return Order.rehydrate(orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getStatus(),
                toOrderItemList(orderEntity.getItems()));
    }

    public static List<OrderItemEntity> toOrderItemEntityList(List<OrderItem> orderItems) {
        return orderItems.stream().map(OrderMapper::toOrderItemEntity).toList();
    }

    public static OrderItemEntity toOrderItemEntity(OrderItem orderItem) {
        return OrderItemEntity.builder().id(orderItem.getId())
                .productId(orderItem.getProductId())
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
        return new OrderItem(orderItemEntity.getId(), orderItemEntity.getProductId(), orderItemEntity.getName(), orderItemEntity.getSku(),
                orderItemEntity.getPrice(), orderItemEntity.getQuantity());
    }
}
