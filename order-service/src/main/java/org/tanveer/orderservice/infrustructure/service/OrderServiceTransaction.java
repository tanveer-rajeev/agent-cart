package org.tanveer.orderservice.infrustructure.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tanveer.orderservice.application.OrderServiceImpl;
import org.tanveer.orderservice.domain.dto.DomainOrderDto;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

@Service
@AllArgsConstructor
public class OrderServiceTransaction implements OrderService {

    private final OrderServiceImpl orderServiceImpl;

    @Override
    @Transactional
    public OrderResponseDto create(DomainOrderDto order) {
        return orderServiceImpl.create(order);
    }
}
