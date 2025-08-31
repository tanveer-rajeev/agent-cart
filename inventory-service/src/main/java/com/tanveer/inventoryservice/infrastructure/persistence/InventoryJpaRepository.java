package com.tanveer.inventoryservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, UUID> {
    Optional<InventoryEntity> findBySku(String sku);
}
