package org.tanveer.orderservice.application;

import org.tanveer.orderservice.infrustructure.dto.OrderRequestDto;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

public interface OrderUseCase {
    OrderResponseDto create(OrderRequestDto order);
}
