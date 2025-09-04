package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface InventoryService {
    Inventory reserveStock(String sku, int quantity) throws InventoryException;

    Inventory releaseStock(String sku, int quantity) throws InventoryException;

    Inventory adjustStock(String sku, int quantity) throws InventoryException;

    Inventory getInventoryBySku(String sku);

    ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto);

    Inventory createInventory(Inventory request);

    Inventory updateInventory(Inventory inventory);
}
