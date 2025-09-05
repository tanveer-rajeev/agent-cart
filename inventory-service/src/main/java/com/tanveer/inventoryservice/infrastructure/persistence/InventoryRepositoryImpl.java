package com.tanveer.inventoryservice.infrastructure.persistence;

import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryJpaRepository repository;

    @Override
    public Inventory findBySku(String sku) throws InventoryException {
       return repository.findBySku(sku)
                .map(InventoryMapper::entityToDomain)
                .orElseThrow(()-> new InventoryException("Inventory not found by sku"));
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
    public boolean isProductAvailable(String sku, int quantity) throws InventoryException {
        return findBySku(sku).getAvailableQty() > quantity;
    }
}
