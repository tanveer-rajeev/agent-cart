package com.tanveer.inventoryservice.infrustructure.config;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.application.InventoryServiceImpl;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public InventoryServiceImpl inventoryService(InventoryRepository inventoryRepository,
                                             EventRepository<InventoryEvent> eventRepository) {
        return new InventoryServiceImpl(inventoryRepository, eventRepository);
    }

}
