package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.tanveer.inventoryservice.domain.EventType.PRODUCT_CREATED;
import static com.tanveer.inventoryservice.domain.EventType.PRODUCT_UPDATED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventConsumer implements EventConsumer {

    private final ObjectMapper objectMapper;
    private final ProductEventHandler productEventHandler;

    @Override
    @KafkaListener(topics = {"product-created", "product-updated"}, groupId = "inventory-service-01")
    public void consume(String message) {
        try {
            log.info("Consumed event : {}", message);

            ProductEventDto event = objectMapper.readValue(message, ProductEventDto.class);

            log.info("Delegating event to handler");

            switch (event.eventType()) {
                case PRODUCT_CREATED -> productEventHandler.handleProductCreated(event);
                case PRODUCT_UPDATED -> productEventHandler.handleProductUpdated(event);
            }
        } catch (Exception e) {
            log.error("Error processing ProductCreated event", e);
        }
    }
}
//TODO: Refactor productEventDto and OrderEventDto
//TODO: Refactor This class coz breaking Open Close Principle
//TODO: Handle event exception
