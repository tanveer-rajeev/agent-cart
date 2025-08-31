package org.tanveer.orderservice.application;

import org.tanveer.orderservice.infrastructure.dto.OrderRequestDto;
import org.tanveer.orderservice.infrastructure.dto.OrderResponseDto;

public interface OrderUseCase {
    OrderResponseDto create(OrderRequestDto order);
}
