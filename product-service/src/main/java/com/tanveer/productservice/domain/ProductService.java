package com.tanveer.productservice.domain;

import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;

public interface ProductService {
  ProductResponseDto addProduct(ProductRequestDto productRequestDto);
  ProductResponseDto updateProduct(String sku);
}
