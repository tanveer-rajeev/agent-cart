package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.dto.OrderRequestDto;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.infrustructure.client.InventoryClient;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityDto;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityResponseDto;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityRequestDto;
import org.tanveer.orderservice.infrustructure.exception.OrderException;
import org.tanveer.orderservice.infrustructure.mapper.OrderMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderResponseDto create(OrderRequestDto orderRequestDto) {
        log.info("Creating order for the customer{} ", orderRequestDto.getCustomerId());

        List<OrderItem> orderItems = orderRequestDto.getItems()
                .stream().map(item -> new OrderItem(item.getId(),item.getProductId(),
                        item.getName(), item.getSku(), item.getPrice(), item.getQuantity()))
                .toList();

        Order order = Order.create(UUID.randomUUID().toString(), orderRequestDto.getCustomerId(), orderItems);

        log.info("Making api call to inventory service to check product availability {}",
                orderRequestDto.getCustomerId());

        ItemAvailabilityResponseDto itemAvailabilityResponseDto =
                inventoryClient.checkProductsAvailability(new ItemAvailabilityRequestDto(orderItems));

        log.info("Get available product list {}", itemAvailabilityResponseDto);

        Optional<ItemAvailabilityDto> unavailableProductList = itemAvailabilityResponseDto.itemAvailabilityDto()
                .stream().filter(product -> !product.isAvailable())
                .findAny();

        if(unavailableProductList.isPresent()) {
            throw new OrderException("Some products not available",unavailableProductList.get());
        }

        log.info("Saving order of customer {}", orderRequestDto.getCustomerId());

        Order saved = orderRepository.save(order);

        log.info("Saving all events of the order of customer {}", orderRequestDto.getCustomerId());

        order.pullOrderEvents().forEach(orderEventRepository::saveEvent);

        return OrderMapper.toResponseDto(saved);
    }
}
