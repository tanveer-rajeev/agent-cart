package com.tanveer.productservice.infrustructure.dto;

public record ProductRequestDto(String name, String description, int quantity, String sku, Long price) {
}

