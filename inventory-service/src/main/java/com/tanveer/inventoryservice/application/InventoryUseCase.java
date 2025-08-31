package com.tanveer.inventoryservice.application;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;

public interface InventoryUseCase {
    InventoryResponseDto reserveStock(String sku, int quantity);
    InventoryResponseDto releaseStock(String sku, int quantity);
    InventoryResponseDto adjustStock(String sku, int quantity);
    InventoryResponseDto getInventoryBySku(String sku);
    ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto);
    InventoryResponseDto createInventory(InventoryRequestDto request);
    Inventory updateInventory(InventoryRequestDto inventory);
}
