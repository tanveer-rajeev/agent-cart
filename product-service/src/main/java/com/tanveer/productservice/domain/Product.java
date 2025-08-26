package com.tanveer.productservice.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Product {

    private final String id;
    private final String name;
    private final String description;
    private final String sku;
    private final Long price;
    private final List<ProductEvent> events;

    private Product(String id, String name, String description, String sku, Long price, List<ProductEvent> events) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.events = List.copyOf(events);
    }

    public static Product create(String id, String name, String description, String sku, Long price) {
        List<ProductEvent> events = new ArrayList<>();
        events.add(new ProductEvent(id, sku, EventType.PRODUCT_CREATED.value(), Instant.now()
        ));
        return new Product(id, name, description, sku, price, events);
    }

    public List<ProductEvent> pullProductEvents() {
        return events;
    }
}
