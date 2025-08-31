package com.tanveer.inventoryservice.infrastructure.dto;

public record InventoryResponseDto(String sku, int availableQty,int reserveQty) {
}
