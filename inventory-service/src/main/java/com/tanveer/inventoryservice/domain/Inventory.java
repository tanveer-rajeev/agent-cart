package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import lombok.Getter;

import java.time.Instant;
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
    private final int version;

    private Inventory(String id, String productId, String sku, int availableQty, int reservedQty,
                      List<InventoryEvent> domainEvents, int version) {
        this.id = id;
        this.productId = productId;
        this.sku = sku;
        this.availableQty = availableQty;
        this.reservedQty = reservedQty;
        this.domainEvents = List.copyOf(domainEvents);
        this.version = version;
    }

    public static Inventory create(String productId, String sku, int availableQty, int reservedQty, int version) {
        String id = UUID.randomUUID().toString();
        InventoryEvent event = new InventoryEvent(id, productId, sku, availableQty, EventType.INVENTORY_CREATED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, reservedQty, List.of(event), version);
    }

    public static Inventory rehydrate(String id, String productId, String sku, int availableQty, int reservedQty,
                                      int version) {
        return new Inventory(id, productId, sku, availableQty, reservedQty, List.of(), version);
    }

    public Inventory reserve(int quantity) throws InventoryException {
        if ((quantity + reservedQty) > availableQty) {
            throw new InventoryException("Insufficient stock");
        }
        InventoryEvent event = new InventoryEvent(id, productId, sku, quantity, EventType.INVENTORY_RESERVED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, quantity + reservedQty, List.of(event), version);
    }

    public Inventory release(int quantity) {
        int available = Math.max(0, reservedQty - quantity);
        InventoryEvent event = new InventoryEvent(id, productId, sku, available, EventType.INVENTORY_RELEASED,
                Instant.now());
        return new Inventory(id, productId, sku, availableQty, available, List.of(event), version);
    }

    public Inventory adjust(int quantity) {
        int available = availableQty + quantity;
        InventoryEvent event = new InventoryEvent(id, productId, sku, available, EventType.INVENTORY_ADJUST,
                Instant.now());
        return new Inventory(id, productId, sku, available, reservedQty, List.of(event), version);
    }

    public List<InventoryEvent> pullDomainEvents() {
        return domainEvents;
    }
}
