package com.tanveer.inventoryservice.infrustructure.dto;

public record InventoryRequestDto(String sku,int availableQty,int reserveQty) {
}
