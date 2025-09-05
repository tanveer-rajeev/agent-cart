package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.service.OrderService;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    @Override
    public Order create(Order order) {

        log.info("Creating order for the customer{} ", order.getCustomerId());

        order = Order.create(order.getCustomerId(), order.getItems());

        log.info("Saving order of customer {}", order.getCustomerId());

        orderRepository.save(order);

        log.info("Saving all events of the order of customer {}", order.getCustomerId());

        order.pullOrderEvents().forEach(orderEventRepository::saveEvent);

        return order;
    }

    @Override
    public Order pendingOrderHandler(Order order) {
        order = Order.pending(order.getCustomerId(), order.getItems());

        log.info("Saving pending order of customer {}", order.getCustomerId());

        orderRepository.save(order);
        return order;
    }

    @Override
    public Order update(Order order, String id) {
        Order byId = orderRepository.findById(id);
        Order domain = Order.update(byId.getOrderId(), byId.getCustomerId(), byId.getItems());

        log.info("Updating order of customer {}", order.getCustomerId());

        orderRepository.update(domain);

        log.info("Saving all events of the updated order");

        order.pullOrderEvents().forEach(orderEventRepository::saveEvent);
        return domain;
    }
}
