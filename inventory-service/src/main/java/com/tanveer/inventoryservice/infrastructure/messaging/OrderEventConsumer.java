package com.tanveer.inventoryservice.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.OrderEventDto;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.tanveer.inventoryservice.domain.ConsumeEventType.ORDER_CANCELED;
import static com.tanveer.inventoryservice.domain.ConsumeEventType.ORDER_PLACED;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventConsumer implements EventConsumer{
    private final ObjectMapper objectMapper;
    private final OrderEventHandler orderEventHandler;

    @Override
    @KafkaListener(topics = {"order-placed","order-canceled"}, groupId = "inventory-service-01")
    public void consume(String message) {
        try {
            log.info("Consumed event : {}", message);

            OrderEventDto event = objectMapper.readValue(message, OrderEventDto.class);

            log.info("Delegating event to handler");

            switch (event.eventType()) {
                case ORDER_PLACED -> orderEventHandler.handleOrderPlaced(event);
                case ORDER_CANCELED -> orderEventHandler.handleOrderCanceled(event);
            }
        } catch (Exception e) {
            log.error("Error processing ProductCreated event", e);
        }
    }
}
