package com.tanveer.agent.infrastructure.dto;

public record InventoryRequestDto(String productId, String sku, int availableQty, int reserveQty,int version) {
}
