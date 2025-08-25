package org.tanveer.orderservice.infrustructure.dto;

public record AvailableProductList(String sku, int requestedQty, int availableQty, boolean isAvailable) {
}
