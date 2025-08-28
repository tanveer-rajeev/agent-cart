package org.tanveer.orderservice.domain.service;

import org.tanveer.orderservice.domain.model.Order;

public interface OrderService {
    Order create(Order order);
}
