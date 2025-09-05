package com.tanveer.inventoryservice.infrastructure.messaging.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.messaging.AbstractEventConsumer;
import com.tanveer.inventoryservice.infrastructure.messaging.EventConsumer;
import com.tanveer.inventoryservice.infrastructure.messaging.EventHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductEventConsumer extends AbstractEventConsumer<ProductEventDto> implements EventConsumer {

    protected ProductEventConsumer(ObjectMapper objectMapper, EventHandlerRegistry<ProductEventDto> handlerRegistry) {
        super(objectMapper, handlerRegistry,  ProductEventDto.class);
    }

    @KafkaListener(topics = {"product-created", "product-updated"}, groupId = "inventory-service-01")
    public void consume(String message) throws InventoryException, JsonProcessingException {
         process(message);
    }
}
