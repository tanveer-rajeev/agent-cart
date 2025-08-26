package com.tanveer.inventoryservice.infrustructure.mapper;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.persistence.InventoryEntity;

public class InventoryMapper {

    public static InventoryEntity toEntity(Inventory domain) {
        return InventoryEntity.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .availableQty(domain.getAvailableQty())
                .reservedQty(domain.getReservedQty())
                .productId(domain.getProductId())
                .build();
    }

    public static Inventory toDomain(InventoryEntity entity) {
        return Inventory.create(
                entity.getId(),
                entity.getProductId(),
                entity.getSku(),
                entity.getAvailableQty(),
                entity.getReservedQty()
        );
    }

    public static InventoryResponseDto toResponseDto(Inventory inventory) {
        return new InventoryResponseDto(
                inventory.getSku(),
                inventory.getAvailableQty(),
                inventory.getReservedQty()
        );
    }
}

