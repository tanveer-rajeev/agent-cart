package com.tanveer.inventoryservice.infrastructure.mapper;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrastructure.persistence.InventoryEntity;

public class InventoryMapper {

    public static InventoryEntity domainToEntity(Inventory domain) {
        return InventoryEntity.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .availableQty(domain.getAvailableQty())
                .reservedQty(domain.getReservedQty())
                .productId(domain.getProductId())
                .version(domain.getVersion())
                .build();
    }

    public static Inventory entityToDomain(InventoryEntity entity) {
        return Inventory.rehydrate(
                entity.getId(),
                entity.getProductId(),
                entity.getSku(),
                entity.getAvailableQty(),
                entity.getReservedQty(),
                entity.getVersion()
        );
    }

    public static InventoryResponseDto domainToResponseDto(Inventory inventory) {
        return new InventoryResponseDto(
                inventory.getSku(),
                inventory.getAvailableQty(),
                inventory.getReservedQty()
        );
    }

    public static Inventory dtoToDomain(InventoryRequestDto request) {
        return Inventory.create(request.productId(),
                request.sku(),
                request.availableQty(),
                request.reserveQty(),
                request.version());
    }

}

