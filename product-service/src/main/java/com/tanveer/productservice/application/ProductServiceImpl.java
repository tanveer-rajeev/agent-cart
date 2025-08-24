package com.tanveer.productservice.application;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;
import com.tanveer.productservice.infrustructure.mapper.ProductMapper;
import com.tanveer.productservice.infrustructure.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl{

  private final ProductRepository repository;
  private final EventRepository<ProductEvent> eventRepository;


  public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {

    log.info("Creating product {}", productRequestDto.sku());

    if (repository.findBySku(productRequestDto.sku()).isPresent()) {
      throw new IllegalArgumentException("SKU already exists");
    }
      Product domain = Product.create(
              UUID.randomUUID(),
              productRequestDto.name(),
              productRequestDto.description(),
              productRequestDto.sku(),
              productRequestDto.price()
      );

    log.info("Saving product {}", productRequestDto.sku());

    repository.save(ProductMapper.toEntity(domain));

    log.info("Adding event for {}", productRequestDto.sku());

    domain.pullDomainEvents().forEach(eventRepository::saveEvent);

    log.info("Saving event entity for {}", productRequestDto.sku());

    return ProductMapper.toResponseDto(domain);
  }


  public ProductResponseDto updateProduct(String sku) {
    return null;
  }

}
