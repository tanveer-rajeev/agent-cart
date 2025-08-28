package com.tanveer.productservice.application.dto;

public record ProductRequestDto(String name, String description, String sku, Long price) {
}

