package com.tanveer.inventoryservice.infrustructure.dto;

import java.util.UUID;

public record InventoryRequestDto(UUID correlationId, String sku, int availableQty, int reserveQty) {
}
