package com.tanveer.inventoryservice.infrastructure.messaging.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.infrastructure.dto.OrderEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.messaging.AbstractEventConsumer;
import com.tanveer.inventoryservice.infrastructure.messaging.EventConsumer;
import com.tanveer.inventoryservice.infrastructure.messaging.EventHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventConsumer extends AbstractEventConsumer<OrderEventDto> implements EventConsumer {

    public OrderEventConsumer(ObjectMapper objectMapper, EventHandlerRegistry<OrderEventDto> handlerRegistry) {
        super(objectMapper, handlerRegistry, OrderEventDto.class);
    }

    @Override
    @KafkaListener(topics = {"order-placed","order-canceled"}, groupId = "inventory-service-01")
    public void consume(String message, Acknowledgment ack) throws InventoryException, JsonProcessingException {
        process(message);
        log.info("Acknowledging the message processed safely");
        ack.acknowledge();
    }
}
