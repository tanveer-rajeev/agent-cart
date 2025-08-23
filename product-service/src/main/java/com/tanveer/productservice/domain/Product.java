package com.tanveer.productservice.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Product {

    private final UUID id;
    private final String name;
    private final String description;
    private final String sku;
    private final Long price;
    private final List<ProductEvent> domainEvents;

    private Product(UUID id, String name, String description, String sku, Long price, List<ProductEvent> domainEvents) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.domainEvents = List.copyOf(domainEvents);
    }


    public static Product create(UUID id, String name, String description, String sku, Long price) {
        List<ProductEvent> events = new ArrayList<>();
        events.add(new ProductEvent(id, sku, EventType.PRODUCT_CREATED.value(), Instant.now()
        ));
        return new Product(id, name, description, sku, price, events);
    }

    public List<ProductEvent> pullDomainEvents() {
        return domainEvents;
    }
}
