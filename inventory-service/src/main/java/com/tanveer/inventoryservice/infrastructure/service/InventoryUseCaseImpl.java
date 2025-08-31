package com.tanveer.inventoryservice.infrastructure.service;

import com.tanveer.inventoryservice.application.InventoryUseCase;
import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryUseCaseImpl implements InventoryUseCase {

    private final InventoryService inventoryService;

    @Override
    public InventoryResponseDto createInventory(InventoryRequestDto request) {
        return InventoryMapper.domainToResponseDto(inventoryService.createInventory(InventoryMapper.dtoToDomain(request)));
    }

    @Override
    public InventoryResponseDto reserveStock(String sku, int quantity) {
        return InventoryMapper.domainToResponseDto(inventoryService.reserveStock(sku,quantity));
    }

    @Override
    public InventoryResponseDto releaseStock(String sku, int quantity) {
        return InventoryMapper.domainToResponseDto(inventoryService.releaseStock(sku,quantity));
    }

    @Override
    @Transactional
    public InventoryResponseDto adjustStock(String sku, int quantity) {
        return InventoryMapper.domainToResponseDto(inventoryService.adjustStock(sku,quantity));
    }

    @Override
    public InventoryResponseDto getInventoryBySku(String sku) {
        return InventoryMapper.domainToResponseDto(inventoryService.getInventoryBySku(sku));
    }

    // TODO: shouldn't be here
    @Override
    public ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto) {
        return inventoryService.checkProductsAvailability(itemAvailabilityRequestDto);
    }

    @Override
    public Inventory updateInventory(InventoryRequestDto inventory) {
        return null;
    }
}
