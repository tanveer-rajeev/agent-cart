package com.tanveer.inventoryservice.domain;

public enum ConsumeEventType {
    PRODUCT_CREATED("product-created"),
    PRODUCT_RESERVED("product-reserved"),
    PRODUCT_UPDATED("product-updated");

    private final String value;

    ConsumeEventType(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
