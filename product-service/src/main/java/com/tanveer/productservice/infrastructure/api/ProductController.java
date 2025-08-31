package com.tanveer.productservice.infrastructure.api;

import com.tanveer.productservice.application.ProductUseCase;
import com.tanveer.productservice.infrastructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrastructure.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductUseCase productUseCase;

  @PostMapping
  public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.ok(productUseCase.addProduct(productRequestDto));
  }
}
