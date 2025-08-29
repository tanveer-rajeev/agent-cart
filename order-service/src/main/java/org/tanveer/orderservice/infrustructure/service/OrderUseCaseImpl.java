package org.tanveer.orderservice.infrustructure.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tanveer.orderservice.application.OrderUseCase;
import org.tanveer.orderservice.infrustructure.dto.OrderRequestDto;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;
import org.tanveer.orderservice.infrustructure.mapper.OrderMapper;

@Service
@AllArgsConstructor
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderService orderService;

    @Override
    @Transactional
    public OrderResponseDto create(OrderRequestDto order) {
        return OrderMapper.domainToResponseDto(orderService.create(OrderMapper.dtoToDomain(order)));
    }
}
