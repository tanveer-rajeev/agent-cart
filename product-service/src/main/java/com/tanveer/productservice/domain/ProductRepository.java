package com.tanveer.productservice.domain;

import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;

public interface ProductRepository {
    Product addProduct(ProductRequestDto productRequestDto);
}
