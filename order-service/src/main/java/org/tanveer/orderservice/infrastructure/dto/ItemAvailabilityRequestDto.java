package org.tanveer.orderservice.infrastructure.dto;

import org.tanveer.orderservice.domain.model.OrderItem;

import java.util.List;

public record ItemAvailabilityRequestDto(List<OrderItem> orderItemList) {
}

