package org.tanveer.orderservice.infrastructure.persistence;

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
@Table(name = "order_events", indexes = {
        @Index(name = "idx_order_events_published", columnList = "published")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventEntity extends EventEntity {

    @Column(name = "customer_id",nullable = false)
    private String customerId;

    @Column(name = "product_id",nullable = false)
    private String productId;

    @Column(name = "sku",nullable = false)
    private String sku;

    @Column(name = "quantity",nullable = false)
    private int quantity;
}
