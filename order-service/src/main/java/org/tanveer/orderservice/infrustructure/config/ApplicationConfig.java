package org.tanveer.orderservice.infrustructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tanveer.orderservice.application.OrderServiceImpl;
import org.tanveer.orderservice.domain.respository.OrderEventRepository;
import org.tanveer.orderservice.domain.respository.OrderRepository;
import org.tanveer.orderservice.infrustructure.client.InventoryClient;

@Configuration
public class ApplicationConfig {

    @Bean
    public OrderServiceImpl orderServiceImpl(InventoryClient inventoryClient, OrderRepository orderRepository,
                                             OrderEventRepository eventRepository) {
        return new OrderServiceImpl(inventoryClient, orderRepository, eventRepository);
    }
}
