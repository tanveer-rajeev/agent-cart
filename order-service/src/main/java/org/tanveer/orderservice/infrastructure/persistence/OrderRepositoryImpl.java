package org.tanveer.orderservice.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.infrastructure.mapper.OrderMapper;

@RequiredArgsConstructor
@Service
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return OrderMapper.entityToDomain(orderJpaRepository.save(OrderMapper.domainToEntity(order)));
    }
}
