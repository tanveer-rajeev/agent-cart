package com.tanveer.inventoryservice.application;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface InventoryUseCase {
    InventoryResponseDto reserveStock(String sku, int quantity) throws InventoryException;
    InventoryResponseDto releaseStock(String sku, int quantity) throws InventoryException;
    InventoryResponseDto adjustStock(String sku, int quantity) throws InventoryException;
    InventoryResponseDto getInventoryBySku(String sku) throws InventoryException;
    ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto) throws InventoryException;
    InventoryResponseDto createInventory(InventoryRequestDto request);
    Inventory updateInventory(InventoryRequestDto inventory);
}
