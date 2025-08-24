package org.tanveer.orderservice.domain.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public final class OrderItem {
    private final UUID productId;
    private final String name;
    private final String sku;
    private final long price;  // cents
    private final int quantity;

    public OrderItem(UUID productId, String name, String sku, long price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.quantity = quantity;
    }

    public long totalPrice() {
        return price * quantity;
    }
}

