package org.tanveer.orderservice.domain.respository;

import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

public interface OrderRepository {
    public Order save(Order order);
}
