package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.domain.model.OrderStatus;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrastructure.client.InventoryClient;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityDto;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityRequestDto;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityResponseDto;
import org.tanveer.orderservice.infrastructure.exception.OrderException;

import java.util.List;
import java.util.Optional;

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
}
