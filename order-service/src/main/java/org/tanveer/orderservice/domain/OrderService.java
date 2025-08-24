package org.tanveer.orderservice.domain;

import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

public interface OrderService {
    public OrderResponseDto create(Order order);
}
