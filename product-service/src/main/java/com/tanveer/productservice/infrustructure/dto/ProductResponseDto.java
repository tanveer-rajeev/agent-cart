package com.tanveer.productservice.infrustructure.dto;

import java.util.UUID;

public record ProductResponseDto(UUID id, String sku, String name, String description, Long price) {
}
