package com.tanveer.inventoryservice.infrastructure.dto;

import java.util.List;

public record ItemAvailabilityRequestDto(List<OrderItem> orderItemList) {
}
