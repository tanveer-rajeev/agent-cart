package com.tanveer.inventoryservice.domain;

public enum ConsumeEventType {
    PRODUCT_CREATED("product-created"),
    ORDER_PLACED("order.placed"),
    ORDER_CANCELED("order.canceled"),
    PRODUCT_UPDATED("product-updated");

    private final String value;

    ConsumeEventType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
