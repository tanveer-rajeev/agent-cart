package com.tanveer.productservice.infrustructure.service;

import com.tanveer.productservice.application.ProductUseCase;
import com.tanveer.productservice.application.dto.ProductRequestDto;
import com.tanveer.productservice.application.dto.ProductResponseDto;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.mapper.ProductMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductUseCaseImpl implements ProductUseCase {

    private final ProductService productService;

    @Override
    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        log.info("Starting transaction for add the {} product", productRequestDto.name());
        return ProductMapper.domainToDto(productService.addProduct(ProductMapper.dtoToDomain(productRequestDto)));
    }

    @Override
    public ProductResponseDto updateProduct(ProductRequestDto productRequestDto) {
        log.info("Starting transaction for update the {} product", productRequestDto.name());
        return ProductMapper.domainToDto(productService.updateProduct(ProductMapper.dtoToDomain(productRequestDto)));
    }
}
