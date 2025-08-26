package org.tanveer.orderservice.infrustructure.dto;

import org.tanveer.orderservice.domain.model.OrderStatus;

public record OrderResponseDto(String orderId, OrderStatus status, long totalPrice) {
}
