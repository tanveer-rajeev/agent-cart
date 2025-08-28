package org.tanveer.orderservice.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public final class OrderItem {
    private final String id;
    private final String productId;
    private final String name;
    private final String sku;
    private final long price;
    private final int quantity;

    public OrderItem(String id, String productId, String name, String sku, long price, int quantity) {
        this.id = id;
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

