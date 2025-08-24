package com.tanveer.productservice.infrustructure.service;

import com.tanveer.productservice.application.ProductServiceImpl;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceTransaction implements ProductService {

    private final ProductServiceImpl productServiceImpl;

    @Override
    @Transactional
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        log.info("Starting transaction for add {} product", productRequestDto.name());
        return productServiceImpl.addProduct(productRequestDto);
    }

    @Override
    public ProductResponseDto updateProduct(String sku) {
        return null;
    }
}
