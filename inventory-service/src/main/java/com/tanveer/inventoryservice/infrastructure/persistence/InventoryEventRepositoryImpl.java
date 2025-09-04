package com.tanveer.inventoryservice.infrastructure.persistence;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.infrastructure.mapper.InventoryEventMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventRepositoryImpl implements EventRepository<InventoryEvent> {

    private final EventJpaRepository repository;

    @Override
    public void saveEvent(InventoryEvent event) {
        log.info("Saving event into database");
        repository.save(InventoryEventMapper.toEntity(event));
    }
}
