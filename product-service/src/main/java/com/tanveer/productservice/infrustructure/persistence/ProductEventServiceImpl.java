package com.tanveer.productservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.productservice.domain.ProductEvent;
import com.tanveer.productservice.infrustructure.mapper.ProductEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventServiceImpl implements EventRepository<ProductEvent> {

  private final EventJpaRepository repository;

  @Override
  public void saveEvent(ProductEvent event) {
    repository.save(ProductEventMapper.toEntity(event));
  }
}
