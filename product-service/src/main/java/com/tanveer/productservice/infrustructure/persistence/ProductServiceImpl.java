package com.tanveer.productservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.dto.ProductRequestDto;
import com.tanveer.productservice.infrustructure.dto.ProductResponseDto;
import com.tanveer.productservice.infrustructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

  private final ProductRepository repository;
  private final EventRepository<ProductEvent> eventRepository;

  @Transactional
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

  @Override
  public ProductResponseDto updateProduct(String sku) {
    return null;
  }

}
