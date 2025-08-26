package com.tanveer.inventoryservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "inventory_events", indexes = {
        @Index(name = "idx_inventory_events_published", columnList = "published")
})
public class InventoryEventEntity extends EventEntity {

    @Column(nullable = false)
    private String sku;

    @Column(name = "event_type")
    private String eventType;

    @Column(name = "product_id",nullable = false)
    private String productId;

    protected InventoryEventEntity() {
        super();
    }
}
