package org.tanveer.orderservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tanveer.orderservice.application.OrderServiceImpl;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.domain.service.OrderService;
import org.tanveer.orderservice.infrastructure.client.InventoryClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public OrderService orderServiceImpl(OrderRepository orderRepository, OrderEventRepository eventRepository) {
        return new OrderServiceImpl(orderRepository, eventRepository);
    }
}
