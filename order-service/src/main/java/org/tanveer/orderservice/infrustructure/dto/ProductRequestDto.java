package org.tanveer.orderservice.infrustructure.dto;

import org.tanveer.orderservice.domain.model.OrderItem;

import java.util.List;

public record ProductRequestDto(List<OrderItem> orderItemList) {
}

