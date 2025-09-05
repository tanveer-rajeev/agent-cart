package org.tanveer.orderservice.infrastructure.dto;

import org.tanveer.orderservice.domain.model.OrderStatus;

import java.util.List;

public record OrderResponseDto(String orderId, OrderStatus status, long totalPrice,
                               List<ItemAvailabilityDto> unavailableItems) {
}
