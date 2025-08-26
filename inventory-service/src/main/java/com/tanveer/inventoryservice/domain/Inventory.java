package com.tanveer.inventoryservice.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Inventory {

    private final String id;
    private final String productId;
    private final String sku;
    private final int availableQty;
    private final int reservedQty;
    private final List<InventoryEvent> domainEvents;

    private Inventory(String id, String productId, String sku, int availableQty, int reservedQty,
                      List<InventoryEvent> domainEvents) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.availableQty = availableQty;
        this.reservedQty = reservedQty;
        this.domainEvents = List.copyOf(domainEvents);
    }

    public static Inventory create(String id, String productId, String sku, int availableQty, int reservedQty) {
        InventoryEvent event = new InventoryEvent(id, productId, sku, availableQty, EventType.INVENTORY_CREATED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, reservedQty, List.of(event));
    }

    public Inventory reserve(int quantity) {
        if (availableQty - quantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        quantity = availableQty - quantity;
        InventoryEvent event = new InventoryEvent(id, productId, sku, quantity, EventType.INVENTORY_RESERVED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, quantity, List.of(event));
    }

    public Inventory release(int quantity) {
        int newReserved = Math.max(0, reservedQty - quantity);
        InventoryEvent event = new InventoryEvent(id, productId, sku, newReserved, EventType.INVENTORY_RELEASED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, newReserved, List.of(event));
    }

    public Inventory adjust(int quantity) {
        int newAvailable = availableQty + quantity;
        InventoryEvent event = new InventoryEvent(id, productId, sku, newAvailable, EventType.INVENTORY_ADJUST,
                Instant.now());
        return new Inventory(id, productId, sku, newAvailable, reservedQty, List.of(event));
    }

    public List<InventoryEvent> pullDomainEvents() {
        return domainEvents;
    }
}
