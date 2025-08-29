package com.tanveer.productservice.application;

import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;

public interface ProductUseCase {
  ProductResponseDto addProduct(ProductRequestDto productRequestDto);
  ProductResponseDto updateProduct(ProductRequestDto productRequestDto);
}
