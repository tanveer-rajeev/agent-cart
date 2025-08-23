package com.tanveer.inventoryservice.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class Inventory {

    private final UUID id;
    private final UUID correlationId;
    private final String sku;
    private final int availableQty;
    private final int reservedQty;
    private final List<InventoryEvent> domainEvents;

    private Inventory(UUID id, UUID correlationId, String sku, int availableQty, int reservedQty, List<InventoryEvent> domainEvents) {
        this.id = id;
        this.correlationId = correlationId;
        this.sku = sku;
        this.availableQty = availableQty;
        this.reservedQty = reservedQty;
        this.domainEvents = List.copyOf(domainEvents);
    }

    public static Inventory create(UUID id, UUID correlationId, String sku, int availableQty, int reservedQty) {
        InventoryEvent event = new InventoryEvent(id, correlationId, sku, availableQty, EventType.INVENTORY_CREATED, Instant.now());
        return new Inventory(id, correlationId, sku, availableQty, reservedQty, new ArrayList<>() {{
            add(event);
        }});
    }

    public Inventory reserve(int quantity) {
        if (availableQty - quantity < quantity) {
            throw new IllegalArgumentException("Insufficient stock");
        }
        quantity = availableQty - quantity;
        InventoryEvent event = new InventoryEvent(id, correlationId, sku, quantity, EventType.INVENTORY_RESERVED, Instant.now());
        return new Inventory(id, correlationId, sku, availableQty, quantity, append(event));
    }

    public Inventory release(int quantity) {
        int newReserved = Math.max(0, reservedQty - quantity);
        InventoryEvent event = new InventoryEvent(id, correlationId, sku, newReserved, EventType.INVENTORY_RELEASED, Instant.now());
        return new Inventory(id, correlationId, sku, availableQty, newReserved, append(event));
    }

    public Inventory adjust(int quantity) {
        int newAvailable = availableQty + quantity;
        InventoryEvent event = new InventoryEvent(id, correlationId, sku, newAvailable, EventType.INVENTORY_ADJUST, Instant.now());
        return new Inventory(id, correlationId, sku, newAvailable, reservedQty, append(event));
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
