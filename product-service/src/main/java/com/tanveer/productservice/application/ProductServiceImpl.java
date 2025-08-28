package com.tanveer.productservice.application;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.domain.ProductService;
import com.tanveer.productservice.infrustructure.mapper.ProductMapper;
import com.tanveer.productservice.infrustructure.persistence.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductJpaRepository repository;
    private final EventRepository<ProductEvent> eventRepository;

    @Override
    public Product addProduct(Product product) {
        log.info("Creating product {}", product.getSku());

        if (repository.findBySku(product.getSku()).isPresent()) {
            throw new IllegalArgumentException("SKU already exists");
        }

        Product domain = Product.create(
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getPrice()
        );

        log.info("Saving product {}", product.getSku());

        repository.save(ProductMapper.domainToEntity(domain));

        log.info("Adding event for {}", product.getSku());

        domain.pullProductEvents().forEach(eventRepository::saveEvent);

        return domain;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

}
