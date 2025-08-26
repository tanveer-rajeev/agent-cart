package org.tanveer.orderservice.infrustructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tanveer.orderservice.domain.model.OrderStatus;

import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_customerId", columnList = "customerId")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    private UUID orderId;
    private UUID customerId;
    private OrderStatus status;
    @OneToMany(targetEntity = OrderItemEntity.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
