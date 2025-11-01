package com.tanveer.inventoryservice.infrastructure.dto;

public record InventoryRequestDto(String productId, String name, String description, double price,
                                  String sku, int availableQty, int reserveQty, int version) {
}
