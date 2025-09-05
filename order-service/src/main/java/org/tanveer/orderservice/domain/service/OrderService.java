package org.tanveer.orderservice.domain.service;

import org.tanveer.orderservice.domain.model.Order;

public interface OrderService {
    Order create(Order order);

    Order pendingOrderHandler(Order order);

    Order update(Order order, String id);
}
