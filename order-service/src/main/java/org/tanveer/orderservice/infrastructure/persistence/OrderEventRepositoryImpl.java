package org.tanveer.orderservice.infrastructure.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tanveer.orderservice.domain.model.OrderEvent;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.infrastructure.mapper.OrderMapper;

@Service
@AllArgsConstructor
@Slf4j
public class OrderEventRepositoryImpl implements OrderEventRepository {

    private final OrderEventJpaRepository orderEventJpaRepository;

    @Override
    public void saveEvent(OrderEvent event) {
        OrderEventEntity save = orderEventJpaRepository.save(OrderMapper.toEventEntity(event));
        log.info("Saved order event {}", save);
    }
}
