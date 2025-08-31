package com.tanveer.inventoryservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventories", indexes = {
        @Index(name = "idx_inventories_sku", columnList = "sku")
})
public class InventoryEntity {

    @Id
    private String id;

    @Column(name = "product_id", unique = true, nullable = false)
    private String productId;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(name = "available_qty", nullable = false)
    private int availableQty;

    @Column(name = "reserved_qty", nullable = false)
    private int reservedQty;

    @Version
    private int version;
}
