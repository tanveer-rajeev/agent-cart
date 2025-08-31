package com.tanveer.productservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "products",indexes = {
  @Index(name = "idx_products_sku", columnList = "sku")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {

  @Id
  private String id;
  private String name;
  private String description;
  @Column(unique = true, nullable = false)
  private String sku;
  private Long price;
}
