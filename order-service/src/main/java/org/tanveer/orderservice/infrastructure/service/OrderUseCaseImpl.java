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
import org.tanveer.orderservice.infrastructure.exception.OrderException;
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
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "pendingOrderHandler")
    public OrderResponseDto create(OrderRequestDto order) {
        Order domain = OrderMapper.dtoToDomain(order);
        log.info("Making api call to inventory service to check product availability {}",
                order.getCustomerId());

        ItemAvailabilityResponseDto itemAvailabilityResponseDto =
                inventoryClient.checkProductsAvailability(new ItemAvailabilityRequestDto(domain.getItems()));

        log.info("Get availability response result {}", itemAvailabilityResponseDto);

        Optional<ItemAvailabilityDto> unavailableProductList = itemAvailabilityResponseDto.itemAvailabilityDto()
                .stream().filter(product -> !product.isAvailable())
                .findAny();

        if(unavailableProductList.isPresent()) {
            throw new OrderException("Some products not available",unavailableProductList.get());
        }
        return OrderMapper.domainToResponseDto(orderService.create(domain));
    }

    @Transactional
    private OrderResponseDto pendingOrderHandler(OrderRequestDto order, Throwable ex) {
        log.error("Inventory service unavailable, saving order as PENDING", ex);
        return OrderMapper.domainToResponseDto(orderService.pendingOrderHandler(OrderMapper.dtoToDomain(order)));
    }
}
