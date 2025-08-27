package org.tanveer.orderservice.infrustructure.dto;

public record ItemAvailabilityDto(String sku, int requestedQty, int availableQty, boolean isAvailable) {
}
