package org.tanveer.orderservice.domain.service;

import org.tanveer.orderservice.domain.dto.DomainOrderDto;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto create(DomainOrderDto order);
}
