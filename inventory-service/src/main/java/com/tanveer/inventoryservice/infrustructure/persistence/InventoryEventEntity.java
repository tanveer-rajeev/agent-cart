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

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@Entity
@Table(name = "inventory_events", indexes = {
  @Index(name = "idx_inventory_events_sku_published", columnList = "sku, published")
})
public class InventoryEventEntity extends EventEntity {

  @Column(unique = true, nullable = false)
  private String sku;

  @Column(name = "event_type")
  private String eventType;
  protected InventoryEventEntity(){
    super();
  }
}
