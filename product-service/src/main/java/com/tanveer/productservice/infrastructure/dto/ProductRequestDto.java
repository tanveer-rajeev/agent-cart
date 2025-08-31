package com.tanveer.productservice.infrastructure.dto;

public record ProductRequestDto(String name, String description, String sku, Long price) {
}

