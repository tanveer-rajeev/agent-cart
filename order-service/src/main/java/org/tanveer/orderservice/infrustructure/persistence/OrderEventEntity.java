package org.tanveer.orderservice.infrustructure.persistence;

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

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "order_events", indexes = {
        @Index(name = "idx_product_events_customerId_productId", columnList = "customerId, productId")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEventEntity extends EventEntity {

    @Column(name = "customer_id", unique = true, nullable = false)
    private UUID customerId;

    @Column(name = "product_id", unique = true, nullable = false)
    private UUID productId;
}
