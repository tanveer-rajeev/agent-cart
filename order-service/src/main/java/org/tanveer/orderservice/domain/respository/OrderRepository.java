package org.tanveer.orderservice.domain.respository;

import org.tanveer.orderservice.domain.model.Order;

public interface OrderRepository {
    Order save(Order order);
}
