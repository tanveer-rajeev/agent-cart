package org.tanveer.orderservice.infrustructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.infrustructure.mapper.OrderMapper;

@RequiredArgsConstructor
@Service
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return OrderMapper.toDomain(orderJpaRepository.save(OrderMapper.toEntity(order)));
    }
}
