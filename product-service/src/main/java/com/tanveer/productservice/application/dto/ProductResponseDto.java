package com.tanveer.productservice.application.dto;

public record ProductResponseDto(String id, String sku, String name, String description, Long price) {
}
