package com.tanveer.productservice.application;

import com.tanveer.productservice.application.dto.ProductRequestDto;
import com.tanveer.productservice.application.dto.ProductResponseDto;

public interface ProductUseCase {
  ProductResponseDto addProduct(ProductRequestDto productRequestDto);
  ProductResponseDto updateProduct(ProductRequestDto productRequestDto);
}
