package com.tanveer.inventoryservice.infrustructure.dto;

import java.util.List;

public record ItemAvailabilityRequestDto(List<OrderItem> orderItemList) {
}
