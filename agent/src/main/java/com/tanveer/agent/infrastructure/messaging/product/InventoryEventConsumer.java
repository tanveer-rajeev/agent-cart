package com.tanveer.agent.infrastructure.messaging.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.agent.infrastructure.dto.InventoryEventDto;
import com.tanveer.agent.infrastructure.exception.AgentException;
import com.tanveer.agent.infrastructure.messaging.AbstractEventConsumer;
import com.tanveer.agent.infrastructure.messaging.EventConsumer;
import com.tanveer.agent.infrastructure.messaging.EventHandlerRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryEventConsumer extends AbstractEventConsumer<InventoryEventDto> implements EventConsumer {

    protected InventoryEventConsumer(ObjectMapper objectMapper, EventHandlerRegistry<InventoryEventDto> handlerRegistry) {
        super(objectMapper, handlerRegistry, InventoryEventDto.class);
    }

    @KafkaListener(topics = {"inventory-adjust", "inventory-reserved"}, groupId = "agent-01")
    public void consume(String message, Acknowledgment ack) throws AgentException, JsonProcessingException {
        process(message);
        log.info("Acknowledging the message processed safely");
        ack.acknowledge();
    }
}
