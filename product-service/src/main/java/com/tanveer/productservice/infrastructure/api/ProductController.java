package com.tanveer.productservice.infrastructure.api;

import com.tanveer.productservice.application.ProductUseCase;
import com.tanveer.productservice.infrastructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrastructure.dto.ProductResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Product service APIs")
public class ProductController {

  private final ProductUseCase productUseCase;

  @PostMapping
  @Operation(summary = "For add a product",description = "Accepts a product request for add a product")
  public ResponseEntity<ProductResponseDto> addProduct(@RequestBody ProductRequestDto productRequestDto) {
    return ResponseEntity.ok(productUseCase.addProduct(productRequestDto));
  }
}
