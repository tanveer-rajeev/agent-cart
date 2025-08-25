package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.AvailableProductResponseDto;

public interface InventoryService {
  InventoryResponseDto reserveStock(String sku, int quantity);
  InventoryResponseDto releaseStock(String sku, int quantity);
  InventoryResponseDto adjustStock(String sku, int quantity);
  InventoryResponseDto getInventoryBySku(String sku);
  AvailableProductResponseDto checkProductsAvailability(ProductRequestDto productRequestDto);
  InventoryResponseDto createInventory(InventoryRequestDto request);
  Inventory updateInventory(Inventory inventory);
}
