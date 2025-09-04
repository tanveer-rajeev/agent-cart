package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

@FunctionalInterface
public interface InventoryAction {
    Inventory apply(Inventory inventory) throws InventoryException;
}