package com.tanveer.productservice.infrustructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductEventJpaRepository extends JpaRepository<ProductEventEntity, UUID> {
  List<ProductEventEntity> findByPublishedFalse();
  List<ProductEventEntity> findTop50ByPublishedFalseOrderByOccurredAtAsc();
}
