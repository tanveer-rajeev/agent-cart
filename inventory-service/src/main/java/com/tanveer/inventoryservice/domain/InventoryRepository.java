package com.tanveer.inventoryservice.domain;

public interface InventoryRepository {
  Inventory findBySku(String sku);
  Inventory save(Inventory inventory);
  boolean isProductAvailable(String sku,int quantity);
  Inventory update(Inventory inventory);
}
