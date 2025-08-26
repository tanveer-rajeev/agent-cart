package com.tanveer.productservice.infrustructure.dto;

public record ProductResponseDto(String id, String sku, String name, String description, Long price) {
}
