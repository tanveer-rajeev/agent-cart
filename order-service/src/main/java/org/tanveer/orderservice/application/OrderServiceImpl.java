package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.dto.DomainOrderDto;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.infrustructure.client.InventoryClient;
import org.tanveer.orderservice.infrustructure.dto.AvailableProductList;
import org.tanveer.orderservice.infrustructure.dto.AvailableProductResponseDto;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;
import org.tanveer.orderservice.infrustructure.dto.ProductRequestDto;
import org.tanveer.orderservice.infrustructure.exception.OrderException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderResponseDto create(DomainOrderDto domainOrderDto) {
        log.info("Creating order for the customer{} ", domainOrderDto.getCustomerId());

        List<OrderItem> orderItems = domainOrderDto.getItems()
                .stream().map(item -> new OrderItem(item.getProductId(),
                        item.getName(), item.getSku(), item.getPrice(), item.getQuantity()))
                .toList();

        Order order = Order.create(UUID.randomUUID(), domainOrderDto.getCustomerId(), orderItems);

        AvailableProductResponseDto availableProductResponseDto =
                inventoryClient.checkProductsAvailability(new ProductRequestDto(orderItems));

        Optional<AvailableProductList> unavailableProductList = availableProductResponseDto.availableProductLists()
                .stream().filter(product -> !product.isAvailable())
                .findAny();

        if(unavailableProductList.isPresent()) {
            throw new OrderException("Some products not available",unavailableProductList.get());
        }

        log.info("Saving order of customer {}", domainOrderDto.getCustomerId());

        orderRepository.save(order);

        log.info("Saving all events of the order of customer {}", domainOrderDto.getCustomerId());

        order.pullOrderEvents().forEach(orderEventRepository::saveEvent);

        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus(),
                order.calculateTotalAmount()
        );
    }
}
