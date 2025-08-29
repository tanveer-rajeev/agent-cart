package com.tanveer.productservice.infrustructure.dto;

public record ProductRequestDto(String name, String description, String sku, Long price) {
}

