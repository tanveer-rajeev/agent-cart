package com.tanveer.productservice.infrustructure.dto;

import java.util.UUID;

public record ProductResponseDto(UUID id, String sku, String name, int quantity, String description, Long price) {
}
