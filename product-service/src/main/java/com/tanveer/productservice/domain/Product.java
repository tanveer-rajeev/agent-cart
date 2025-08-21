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
  private final int quantity;
  private final String sku;
  private final Long price;
  private final List<ProductEvent> domainEvents;

  private Product(UUID id, String name, String description, int quantity, String sku,
                  Long price, List<ProductEvent> domainEvents) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.sku = sku;
    this.price = price;
    this.domainEvents = List.copyOf(domainEvents);
  }

  public static Product create(UUID id, String name, String description, int quantity, String sku, Long price) {
    return new Product(id, name, description, quantity, sku, price, List.of());
  }

  public static ProductEvent createEvent(UUID id, String sku, int quantity, String eventType, Instant occurredAt) {
    return new ProductEvent(
      id, sku, quantity, eventType, occurredAt
    );
  }

  public Product addDomainEvent(ProductEvent event) {
    return new Product(id, name, description, quantity, sku, price, appendEvent(createEvent(
      event.getAggregateId(), event.sku(), event.quantity(), event.eventType(), event.getOccurredAt()
    )));
  }

  private List<ProductEvent> appendEvent(ProductEvent event) {
    List<ProductEvent> newList = new ArrayList<>(domainEvents);
    newList.add(event);
    return newList;
  }

  public List<ProductEvent> pullDomainEvents() {
    return domainEvents;
  }
}
