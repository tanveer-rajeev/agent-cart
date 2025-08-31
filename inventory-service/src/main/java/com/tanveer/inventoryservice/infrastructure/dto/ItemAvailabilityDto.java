package com.tanveer.inventoryservice.infrastructure.dto;

public record ItemAvailabilityDto(String sku, int requestedQty, int availableQty, boolean isAvailable) {
}
