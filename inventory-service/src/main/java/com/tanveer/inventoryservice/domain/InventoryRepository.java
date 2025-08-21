package com.tanveer.inventoryservice.domain;

public interface InventoryRepository {
  Inventory findBySku(String sku);
  Inventory save(Inventory inventory);
}
