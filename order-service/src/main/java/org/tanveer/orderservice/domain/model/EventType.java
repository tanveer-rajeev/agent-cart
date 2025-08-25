package org.tanveer.orderservice.domain.model;

public enum EventType {
    ORDER_PLACED("order-placed"),
    ORDER_CONFIRMED("order-confirmed"),
    ORDER_CANCELED("order-canceled");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
