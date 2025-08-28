package com.tanveer.productservice.infrustructure.mapper;

import com.tanveer.productservice.application.dto.ProductRequestDto;
import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.infrustructure.persistence.ProductEntity;
import com.tanveer.productservice.application.dto.ProductResponseDto;

public class ProductMapper {

    public static ProductEntity domainToEntity(Product domain) {
        return ProductEntity.builder()
                .id(domain.getId())
                .sku(domain.getSku())
                .name(domain.getName())
                .description(domain.getDescription())
                .price(domain.getPrice())
                .build();
    }

    public static Product entityToDomain(ProductEntity entity) {
        return Product.create(
                entity.getName(),
                entity.getDescription(),
                entity.getSku(),
                entity.getPrice()
        );
    }

    public static Product dtoToDomain(ProductRequestDto requestDto) {
        return Product.create(
                requestDto.name(),
                requestDto.description(),
                requestDto.sku(),
                requestDto.price()
        );
    }

    public static ProductResponseDto domainToDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }
}

