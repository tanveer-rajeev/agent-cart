package org.tanveer.orderservice.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tanveer.orderservice.domain.model.Order;
import org.tanveer.orderservice.domain.OrderEventRepository;
import org.tanveer.orderservice.domain.OrderRepository;
import org.tanveer.orderservice.domain.dto.DomainOrderDto;
import org.tanveer.orderservice.domain.model.OrderItem;
import org.tanveer.orderservice.infrustructure.dto.OrderResponseDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderResponseDto create(DomainOrderDto domainOrderDto){
        log.info("Creating order for the customer{} ",domainOrderDto.getCustomerId());

        List<OrderItem> orderItems = domainOrderDto.getItems()
                .stream().map(item -> new OrderItem(item.getProductId(),
                        item.getName(), item.getSku(), item.getPrice(), item.getQuantity()))
                .toList();

        Order order = Order.create(UUID.randomUUID(), domainOrderDto.getCustomerId(), orderItems);
        orderRepository.save(order);

        // Persist domain events
//        orderEventRepository.saveAll(order.pullDomainEvents());

        return new OrderResponseDto(
                order.getOrderId(),
                order.getStatus(),
                order.calculateTotalAmount()
        );
    }
}
