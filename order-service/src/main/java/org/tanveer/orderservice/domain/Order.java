package org.tanveer.orderservice.domain;

import com.tanveer.commonlib.domain.DomainEvent;
import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Order {
    private final UUID aggregateId;
    private final UUID customerId;
    private final UUID productId;
    private final OrderStatus status;
    private final List<OrderEvent> events;

    public Order(UUID aggregateId, UUID customerId, UUID productId, OrderStatus status, List<OrderEvent> events) {
        this.aggregateId = aggregateId;
        this.customerId = customerId;
        this.productId = productId;
        this.status = status;
        this.events = List.copyOf(events);
    }

    public static Order create(UUID aggregateId, UUID customerId, UUID productId, OrderStatus status, List<OrderEvent> events) {
        List<OrderEvent> orderEvents = new ArrayList<>();
        orderEvents.add(new OrderEvent(aggregateId, customerId, productId, EventType.ORDER_PLACED, Instant.now()));
        return new Order(aggregateId, customerId, productId, OrderStatus.ORDER_PLACED, orderEvents);
    }
}
