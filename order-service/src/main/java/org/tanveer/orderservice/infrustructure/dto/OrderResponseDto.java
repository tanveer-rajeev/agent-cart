package org.tanveer.orderservice.infrustructure.dto;

import org.tanveer.orderservice.domain.OrderStatus;

import java.util.UUID;

public record OrderResponseDto(UUID orderId, OrderStatus status, long totalPrice) {
}
