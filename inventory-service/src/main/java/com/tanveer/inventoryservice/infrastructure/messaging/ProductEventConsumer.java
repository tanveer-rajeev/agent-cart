package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventConsumer implements EventConsumer {

    private final ObjectMapper objectMapper;
    private final ProductEventHandler handler;

    @Override
    @KafkaListener(topics = {"product-created", "product-updated", "product-released"}, groupId = "inventory-service-01")
    public void consume(String message) {
        try {
            log.info("Message: {}", message);

            ProductEventDto event = objectMapper.readValue(message, ProductEventDto.class);

            log.info("Deserialized: {}", message);

            switch (event.eventType()) {
                case PRODUCT_CREATED -> handler.handleProductCreated(event);
                case PRODUCT_UPDATED -> handler.handleProductUpdated(event);
                case PRODUCT_RESERVED -> handler.handleProductReleased(event);
            }
        } catch (Exception e) {
            log.error("Error processing ProductCreated event", e);
        }
    }
}
