package com.tanveer.inventoryservice.infrustructure.service;

import com.tanveer.inventoryservice.application.InventoryServiceImpl;
import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceTransaction implements InventoryService {

    private final InventoryServiceImpl inventoryServiceImpl;

    @Override
    public InventoryResponseDto getInventoryBySku(String sku) {
        return inventoryServiceImpl.getInventoryBySku(sku);
    }

    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto request) {
        return inventoryServiceImpl.createInventory(request);
    }

    @Override
    public InventoryResponseDto reserveStock(String sku, int quantity) {
        return inventoryServiceImpl.reserveStock(sku,quantity);
    }

    @Override
    public InventoryResponseDto releaseStock(String sku, int quantity) {
        return inventoryServiceImpl.releaseStock(sku,quantity);
    }

    @Override
    public InventoryResponseDto adjustStock(String sku, int quantity) {
        return inventoryServiceImpl.adjustStock(sku,quantity);
    }

    public Inventory updateInventory(Inventory inventory) {
        log.info("updateInventory in action {}", inventory);
        return inventory;
    }

}
