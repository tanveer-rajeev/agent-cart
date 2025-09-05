package com.tanveer.inventoryservice.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum EventType {
    PRODUCT_CREATED("product-created"),
    ORDER_PLACED("order-placed"),
    ORDER_CANCELED("order-canceled"),
    PRODUCT_UPDATED("product-updated"),
    INVENTORY_CREATED("inventory-created"),
    INVENTORY_RESERVED("inventory-reserved"),
    INVENTORY_RELEASED("inventory-released"),
    INVENTORY_ADJUST("inventory-adjust");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static final Map<String, EventType> LOOKUP =
            Arrays.stream(values()).collect(Collectors.toUnmodifiableMap(EventType::value, eventType ->
                    eventType));

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static EventType fromValue(String value) throws InventoryException {
        EventType eventType = LOOKUP.get(value);
        if (eventType == null) {
            throw new InventoryException("Event type is not exist");
        }
        return eventType;
    }
}
