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
import org.tanveer.orderservice.infrastructure.mapper.OrderMapper;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import static org.tanveer.orderservice.domain.model.EventType.ORDER_PLACED;
import static org.tanveer.orderservice.infrastructure.exception.ErrorMessage.ITEMS_UNAVAILABLE;
import static org.tanveer.orderservice.infrastructure.exception.ErrorMessage.SERVICE_UNAVAILABLE;

@Service
@AllArgsConstructor
@Slf4j
public class OrderUseCaseImpl implements OrderUseCase {
    private final OrderService orderService;
    private final InventoryClient inventoryClient;

    @Override
    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "pendingOrderCreate")
    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        Order domain = OrderMapper.dtoToDomain(orderRequestDto);
        return handleOrder(domain, (order, orderId) -> orderService.create(order));
    }

    @Override
    @Transactional
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "pendingOrderUpdate")
    public OrderResponseDto update(OrderRequestDto orderRequestDto, String id) {
        Order domain = OrderMapper.dtoToDomain(orderRequestDto);
        return handleOrder(domain, (order, orderId) -> orderService.update(order, id));
    }

    private OrderResponseDto handleOrder(Order domain, BiFunction<Order, String, Order> saveFunction) {
        List<ItemAvailabilityDto> itemAvailabilityDto = checkOrderItemAvailability(domain);

        if (!itemAvailabilityDto.isEmpty()) {
            Order pending = orderService.pendingOrderHandler(domain);
            return OrderMapper.domainToResponseDto(pending, itemAvailabilityDto);
        }

        Order savedOrder = saveFunction.apply(domain, null);
        return OrderMapper.domainToResponseDto(savedOrder, Collections.emptyList());
    }

    @Transactional
    private OrderResponseDto pendingOrderCreate(OrderRequestDto order, Throwable ex) {
        log.error("Inventory service unavailable while creating order. Saving order as PENDING.", ex);
        return OrderMapper.domainToResponseDto(orderService.pendingOrderHandler(OrderMapper.dtoToDomain(order)),
                Collections.emptyList());
    }

    @Transactional
    private OrderResponseDto pendingOrderUpdate(OrderRequestDto order, String id, Throwable ex) {
        log.error("Inventory service unavailable while updating order {}. Saving order as PENDING.", id, ex);
        return OrderMapper.domainToResponseDto(orderService.pendingOrderHandler(OrderMapper.dtoToDomain(order)),
                Collections.emptyList());
    }

    @Transactional(readOnly = true)
    private List<ItemAvailabilityDto> checkOrderItemAvailability(Order order) {
        log.info("Checking product availability for customer {}", order.getCustomerId());

        ItemAvailabilityResponseDto availabilityResponse =
                inventoryClient.checkProductsAvailability(new ItemAvailabilityRequestDto(order.getItems()));

        log.info("Received inventory check response {}", availabilityResponse);

        return availabilityResponse.itemAvailabilityDto()
                .stream()
                .filter(product -> !product.isAvailable())
                .toList();
    }
}
