package com.tanveer.inventoryservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.infrustructure.mapper.InventoryEventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository<InventoryEvent> {

  private final EventJpaRepository repository;

  @Override
  public void saveEvent(InventoryEvent event) {
      repository.save(InventoryEventMapper.toEntity(event));
  }
}
