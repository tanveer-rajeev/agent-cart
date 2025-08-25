package com.tanveer.inventoryservice.infrustructure.persistence;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import com.tanveer.inventoryservice.infrustructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryJpaRepository repository;

    @Override
    public Inventory findBySku(String sku) {
        Optional<InventoryEntity> inventoryEntity = repository.findBySku(sku);
        return inventoryEntity.map(InventoryMapper::toDomain).orElseGet(() -> InventoryMapper.toDomain(InventoryEntity.builder().sku(sku).build()));
    }

    @Override
    public Inventory save(Inventory inventory) {
        return InventoryMapper.toDomain(repository.save(InventoryMapper.toEntity(inventory)));
    }

    @Override
    public Inventory update(Inventory inventory) {
        return InventoryMapper.toDomain(repository.saveAndFlush(InventoryMapper.toEntity(inventory)));
    }

    @Override
    public boolean isProductAvailable(String sku, int quantity) {
        return findBySku(sku).getAvailableQty() > quantity;
    }
}
