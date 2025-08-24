package org.tanveer.orderservice.domain.model;

import lombok.Getter;
import org.tanveer.orderservice.domain.EventType;
import org.tanveer.orderservice.domain.OrderEvent;
import org.tanveer.orderservice.domain.OrderStatus;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Getter
public final class Order {
    private final UUID orderId;
    private final UUID customerId;
    private final OrderStatus status;
    private final List<OrderEvent> events;
    private final List<OrderItem> items;

    public Order(UUID orderId, UUID customerId, OrderStatus status, List<OrderEvent> events,
                 List<OrderItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.events = List.copyOf(events);
        this.items = items;
    }

    public static Order create(UUID orderId, UUID customerId, List<OrderItem> items) {

        List<OrderEvent> events = items.stream().map(item -> new OrderEvent(orderId, customerId,
                        item.getProductId(), EventType.ORDER_PLACED, Instant.now()))
                .toList();
        return new Order(orderId, customerId, OrderStatus.ORDER_PLACED, events, items);
    }

    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }

    public long calculateTotalAmount() {
        return items.stream().mapToLong(OrderItem::totalPrice).sum();
    }
}
