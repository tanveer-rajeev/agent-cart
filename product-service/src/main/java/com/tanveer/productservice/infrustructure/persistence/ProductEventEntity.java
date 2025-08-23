package com.tanveer.productservice.infrustructure.persistence;

import com.tanveer.commonlib.domain.EventEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "product_events", indexes = {
  @Index(name = "idx_product_events_sku_published", columnList = "sku, published")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEventEntity extends EventEntity {

  @Column(unique = true,nullable = false)
  private String sku;
}
