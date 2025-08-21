package com.tanveer.inventoryservice.domain;

import java.util.Optional;

public interface InventoryRepository {
  Inventory findBySku(String sku);
  Inventory save(Inventory inventory);
}
