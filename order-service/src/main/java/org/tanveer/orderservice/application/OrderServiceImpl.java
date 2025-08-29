package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrustructure.client.InventoryClient;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityDto;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityRequestDto;
import org.tanveer.orderservice.infrustructure.dto.ItemAvailabilityResponseDto;
import org.tanveer.orderservice.infrustructure.exception.OrderException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    @Override
    public Order create(Order order) {
        log.info("Creating order for the customer{} ", order.getCustomerId());

        List<OrderItem> orderItems = order.getItems()
                .stream().map(item -> new OrderItem(item.getId(),item.getProductId(),
                        item.getName(), item.getSku(), item.getPrice(), item.getQuantity()))
                .toList();

        order = Order.create(order.getCustomerId(), orderItems);

        log.info("Making api call to inventory service to check product availability {}",
                order.getCustomerId());

        ItemAvailabilityResponseDto itemAvailabilityResponseDto =
                inventoryClient.checkProductsAvailability(new ItemAvailabilityRequestDto(orderItems));

        log.info("Get availability response result {}", itemAvailabilityResponseDto);

        Optional<ItemAvailabilityDto> unavailableProductList = itemAvailabilityResponseDto.itemAvailabilityDto()
                .stream().filter(product -> !product.isAvailable())
                .findAny();

        if(unavailableProductList.isPresent()) {
            throw new OrderException("Some products not available",unavailableProductList.get());
        }

        log.info("Saving order of customer {}", order.getCustomerId());

        orderRepository.save(order);

        log.info("Saving all events of the order of customer {}", order.getCustomerId());

        order.pullOrderEvents().forEach(orderEventRepository::saveEvent);

        return order;
    }
}
