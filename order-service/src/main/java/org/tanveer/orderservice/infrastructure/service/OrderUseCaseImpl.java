package org.tanveer.orderservice.infrastructure.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tanveer.orderservice.application.OrderUseCase;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.infrastructure.client.InventoryClient;
import org.tanveer.orderservice.infrastructure.dto.*;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrastructure.exception.OrderedItemNotFoundException;
import org.tanveer.orderservice.infrastructure.mapper.OrderMapper;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderUseCaseImpl implements OrderUseCase {

    private final OrderService orderService;
    private final InventoryClient inventoryClient;

    @Override
    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "pendingOrderCreate")
    public OrderResponseDto create(OrderRequestDto order) {
        Order domain = OrderMapper.dtoToDomain(order);
        checkOrderItemAvailability(domain);
        return OrderMapper.domainToResponseDto(orderService.create(domain));
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "pendingOrderUpdate")
    public OrderResponseDto update(OrderRequestDto order, String id) {
        Order domain = OrderMapper.dtoToDomain(order);
        checkOrderItemAvailability(domain);
        return OrderMapper.domainToResponseDto(orderService.update(domain, id));
    }

    @Transactional
    private OrderResponseDto pendingOrderCreate(OrderRequestDto order, Throwable ex) {
        log.error("Inventory service unavailable while creating order. Saving order as PENDING.", ex);
        return OrderMapper.domainToResponseDto(orderService.pendingOrderHandler(OrderMapper.dtoToDomain(order))
        );
    }

    @Transactional
    private OrderResponseDto pendingOrderUpdate(OrderRequestDto order, String id, Throwable ex) {
        log.error("Inventory service unavailable while updating order {}. Saving order as PENDING.", id, ex);
        return OrderMapper.domainToResponseDto(orderService.pendingOrderHandler(OrderMapper.dtoToDomain(order))
        );
    }

    @Transactional(readOnly = true)
    private void checkOrderItemAvailability(Order order) {
        log.info("Checking product availability for customer {}", order.getCustomerId());

        ItemAvailabilityResponseDto availabilityResponse =
                inventoryClient.checkProductsAvailability(new ItemAvailabilityRequestDto(order.getItems()));

        log.info("Received inventory check response {}", availabilityResponse);

        availabilityResponse.itemAvailabilityDto()
                .stream()
                .filter(product -> !product.isAvailable())
                .findAny()
                .ifPresent(unavailable -> {
                    throw new OrderedItemNotFoundException(
                            "Some products are not available", unavailable
                    );
                });
    }
}
