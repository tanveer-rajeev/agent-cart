package com.tanveer.productservice.infrustructure.persistence;

import com.tanveer.productservice.domain.Product;
import com.tanveer.productservice.domain.ProductRepository;
import com.tanveer.productservice.infrustructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository repository;

    @Override
    public Product addProduct(Product product) {
        return ProductMapper.entityToDomain(repository.save(ProductMapper.domainToEntity(product)));
    }
}
