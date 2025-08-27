package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityResponseDto;

public interface InventoryService {
  InventoryResponseDto reserveStock(String sku, int quantity);
  InventoryResponseDto releaseStock(String sku, int quantity);
  InventoryResponseDto adjustStock(String sku, int quantity);
  InventoryResponseDto getInventoryBySku(String sku);
  ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto);
  InventoryResponseDto createInventory(InventoryRequestDto request);
  Inventory updateInventory(Inventory inventory);
}
