package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface InventoryService {
    Inventory reserveStock(String sku, int quantity) throws InventoryException;

    Inventory releaseStock(String sku, int quantity) throws InventoryException;

    Inventory adjustStock(String sku, int quantity) throws InventoryException;

    Inventory getInventoryBySku(String sku) throws InventoryException;

    ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto) throws InventoryException;

    Inventory createInventory(Inventory request);

    Inventory updateInventory(Inventory inventory);
}
