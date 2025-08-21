package com.tanveer.inventoryservice.infrustructure.dto;

public record InventoryResponseDto(String sku, int availableQty,int reserveQty) {
}
