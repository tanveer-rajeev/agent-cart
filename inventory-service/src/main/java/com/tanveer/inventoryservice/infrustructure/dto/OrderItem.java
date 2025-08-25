package com.tanveer.inventoryservice.infrustructure.dto;

import java.util.UUID;

public record OrderItem(UUID productId, String name, String sku, int quantity, int price) {
}
