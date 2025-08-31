package com.tanveer.productservice.infrastructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.infrastructure.mapper.ProductEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventRepositoryImpl implements EventRepository<ProductEvent> {

    private final ProductEventJpaRepository repository;

    @Override
    public void saveEvent(ProductEvent event) {
        repository.save(ProductEventMapper.toEntity(event));
    }
}
