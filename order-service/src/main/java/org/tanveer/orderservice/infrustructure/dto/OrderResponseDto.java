package org.tanveer.orderservice.infrustructure.dto;

import org.tanveer.orderservice.domain.model.OrderStatus;

import java.util.UUID;

public record OrderResponseDto(UUID orderId, OrderStatus status, long totalPrice) {
}
