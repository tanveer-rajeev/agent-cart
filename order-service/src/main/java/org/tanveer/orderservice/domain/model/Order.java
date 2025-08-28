package org.tanveer.orderservice.domain.model;

import lombok.Getter;

import java.time.Instant;
import java.util.*;

@Getter
public final class Order {
    private final String orderId;
    private final String customerId;
    private final OrderStatus status;
    private final List<OrderEvent> events;
    private final List<OrderItem> items;

    public Order(String orderId, String customerId, OrderStatus status, List<OrderEvent> events,
                 List<OrderItem> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.events = List.copyOf(events);
        this.items = items;
    }

    public static Order create(String customerId, List<OrderItem> items) {
        String orderId = UUID.randomUUID().toString();
        List<OrderEvent> events = items.stream().map(item -> new OrderEvent(orderId, customerId,
                        item.getProductId(), EventType.ORDER_PLACED, Instant.now()))
                .toList();
        return new Order(orderId, customerId, OrderStatus.ORDER_PLACED, events, items);
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long calculateTotalAmount() {
        return items.stream().mapToLong(OrderItem::totalPrice).sum();
    }

    public List<OrderEvent> pullOrderEvents() {
        return events;
    }
}
