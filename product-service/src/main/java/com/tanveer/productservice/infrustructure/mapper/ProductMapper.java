package com.tanveer.productservice.infrustructure.mapper;

import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.infrustructure.persistence.ProductEntity;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;

public class ProductMapper {

    public static ProductEntity toEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .build();
    }

    public static Product toDomain(ProductEntity entity) {
        return Product.create(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getSku(),
                entity.getPrice()
        );
    }

    public static ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}

