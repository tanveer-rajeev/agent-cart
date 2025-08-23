package com.tanveer.inventoryservice.infrustructure.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.application.InventoryServiceImpl;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import com.tanveer.inventoryservice.domain.InventoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public InventoryService inventoryService(InventoryRepository inventoryRepository, EventRepository eventRepository) {
        return new InventoryServiceImpl(inventoryRepository, eventRepository);
    }

    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

}
