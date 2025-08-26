package org.tanveer.orderservice.domain.service;

import org.tanveer.orderservice.domain.dto.OrderRequestDto;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto create(OrderRequestDto order);
}
