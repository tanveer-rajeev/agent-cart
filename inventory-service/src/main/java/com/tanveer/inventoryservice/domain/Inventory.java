package com.tanveer.inventoryservice.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public final class Inventory {

  private final UUID id;
  private final String sku;
  private final int availableQty;
  private final int reservedQty;
  private final int version;
  private final List<InventoryEvent> domainEvents;

  private Inventory(UUID id, String sku, int availableQty, int reservedQty, int version, List<InventoryEvent> domainEvents) {
    this.id = id;
    this.sku = sku;
    this.availableQty = availableQty;
    this.reservedQty = reservedQty;
    this.version = version;
    this.domainEvents = List.copyOf(domainEvents);
  }

  public static Inventory create(UUID id, String sku, int availableQty, int reservedQty, int version) {
    return new Inventory(id, sku, availableQty, reservedQty, version, List.of());
  }

  public Inventory reserve(int quantity) {
    if (availableQty - reservedQty < quantity) {
      throw new IllegalArgumentException("Insufficient stock");
    }
    InventoryEvent event = new InventoryEvent(id, sku, EventType.INVENTORY_RESERVED, Instant.now());
    return new Inventory(id, sku, availableQty, reservedQty + quantity, version, append(event));
  }

  public Inventory release(int quantity) {
    int newReserved = Math.max(0, reservedQty - quantity);
    InventoryEvent event = new InventoryEvent(id, sku, EventType.INVENTORY_RELEASED, Instant.now());
    return new Inventory(id, sku, availableQty, newReserved, version, append(event));
  }

  public Inventory adjust(int quantity) {
    int newAvailable = availableQty + quantity;
    InventoryEvent event = new InventoryEvent(id, sku, EventType.PRODUCT_CREATED, Instant.now());
    return new Inventory(id, sku, newAvailable, reservedQty, version, append(event));
  }

  public List<InventoryEvent> pullDomainEvents() {
    return domainEvents;
  }

  private List<InventoryEvent> append(InventoryEvent event) {
    return new java.util.ArrayList<>() {{
      addAll(domainEvents);
      add(event);
    }};
  }
}
