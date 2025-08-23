package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;

public interface InventoryService {
  InventoryResponseDto reserveStock(String sku, int quantity);
  InventoryResponseDto releaseStock(String sku, int quantity);
  InventoryResponseDto adjustStock(String sku, int quantity);
  InventoryResponseDto getInventoryBySku(String sku);
  InventoryResponseDto createInventory(InventoryRequestDto request);
  Inventory updateInventory(Inventory inventory);
}
