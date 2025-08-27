package org.tanveer.orderservice.infrustructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Entity
@Table(name = "order_item",indexes = {
        @Index(name = "idx_products_sku", columnList = "sku")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    private  String productId;
    private  String name;
    private  String sku;
    private  long price;
    private  int quantity;
}
