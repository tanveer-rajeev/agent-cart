package com.tanveer.inventoryservice.infrustructure.dto;

public record InventoryRequestDto(String productId, String sku, int availableQty, int reserveQty) {
}
