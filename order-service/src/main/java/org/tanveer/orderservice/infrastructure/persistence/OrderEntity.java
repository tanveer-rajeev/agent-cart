package org.tanveer.orderservice.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tanveer.orderservice.domain.model.OrderStatus;

import java.util.List;

@Builder
@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_orders_customer_id", columnList = "customer_id")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {

    @Id
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_items", nullable = false)
    @OneToMany(targetEntity = OrderItemEntity.class, cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderItemEntity> items;
}
