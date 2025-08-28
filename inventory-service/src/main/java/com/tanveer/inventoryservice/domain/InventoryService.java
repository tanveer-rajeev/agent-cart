package com.tanveer.inventoryservice.domain;

import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityResponseDto;

public interface InventoryService {
    Inventory reserveStock(String sku, int quantity);

    Inventory releaseStock(String sku, int quantity);

    Inventory adjustStock(String sku, int quantity);

    Inventory getInventoryBySku(String sku);

    ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto);

    Inventory createInventory(Inventory request);

    Inventory updateInventory(Inventory inventory);
}
