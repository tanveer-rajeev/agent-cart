package com.tanveer.inventoryservice.infrustructure.dto;

import java.util.List;

public record ProductRequestDto(List<OrderItem> orderItemList) {
}
