package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface InventoryRepository {
  Inventory findBySku(String sku) throws InventoryException;
  Inventory save(Inventory inventory);
  boolean isProductAvailable(String sku,int quantity) throws InventoryException;
  Inventory update(Inventory inventory);
}
