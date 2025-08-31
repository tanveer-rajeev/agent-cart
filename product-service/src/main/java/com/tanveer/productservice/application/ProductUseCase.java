package com.tanveer.productservice.application;

import com.tanveer.productservice.infrastructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrastructure.dto.ProductResponseDto;

public interface ProductUseCase {
  ProductResponseDto addProduct(ProductRequestDto productRequestDto);
  ProductResponseDto updateProduct(ProductRequestDto productRequestDto);
}
