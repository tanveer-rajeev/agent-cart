package com.tanveer.productservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.EventType;
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

import java.time.Instant;

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

    ProductEntity product = ProductEntity.builder()
      .name(productRequestDto.name())
      .description(productRequestDto.description())
      .quantity(productRequestDto.quantity())
      .sku(productRequestDto.sku())
      .price(productRequestDto.price())
      .build();

    log.info("Saving product {}", productRequestDto.sku());

    repository.save(product);

    log.info("Adding event for {}", productRequestDto.sku());

    Product domain = ProductMapper.toDomain(product);

    ProductEvent event = Product.createEvent(domain.getId(), domain.getSku(), domain.getQuantity(),
      EventType.PRODUCT_CREATED.value(), Instant.now());

    domain = domain.addDomainEvent(event);

    domain.pullDomainEvents().forEach(eventRepository::saveEvent);

    log.info("Saving event entity for {}", productRequestDto.sku());

    return ProductMapper.toResponseDto(domain);
  }

  @Override
  public ProductResponseDto updateProduct(String sku) {
    return null;
  }

}
