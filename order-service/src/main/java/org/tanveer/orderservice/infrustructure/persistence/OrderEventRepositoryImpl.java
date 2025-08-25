package org.tanveer.orderservice.infrustructure.persistence;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.tanveer.orderservice.domain.model.OrderEvent;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.infrustructure.mapper.OrderMapper;

@Service
@AllArgsConstructor
public class OrderEventRepositoryImpl implements OrderEventRepository {

    private final OrderEventJpaRepository orderEventJpaRepository;

    @Override
    public void saveEvent(OrderEvent event) {
        orderEventJpaRepository.save(OrderMapper.toEntity(event));
    }
}
