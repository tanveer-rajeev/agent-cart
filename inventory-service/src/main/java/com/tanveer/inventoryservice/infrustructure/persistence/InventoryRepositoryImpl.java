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
       return repository.findBySku(sku)
                .map(InventoryMapper::entityToDomain)
                .orElseThrow(()-> new RuntimeException("Inventory not found by sku"));
    }

    @Override
    public Inventory save(Inventory inventory) {
        return InventoryMapper.entityToDomain(repository.save(InventoryMapper.domainToEntity(inventory)));
    }

    @Override
    public Inventory update(Inventory inventory) {
        return InventoryMapper.entityToDomain(repository.saveAndFlush(InventoryMapper.domainToEntity(inventory)));
    }

    @Override
    public boolean isProductAvailable(String sku, int quantity) {
        return findBySku(sku).getAvailableQty() > quantity;
    }
}
