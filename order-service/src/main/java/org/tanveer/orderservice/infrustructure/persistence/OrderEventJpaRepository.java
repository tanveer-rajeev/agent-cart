package org.tanveer.orderservice.infrustructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderEventJpaRepository extends JpaRepository<OrderEventEntity, UUID> {
    Optional<OrderEventEntity> findByCustomerId(UUID customerId);
    Optional<OrderEventEntity> findByProductId(UUID productId);
    List<OrderEventEntity> findByPublishedFalse();
}
