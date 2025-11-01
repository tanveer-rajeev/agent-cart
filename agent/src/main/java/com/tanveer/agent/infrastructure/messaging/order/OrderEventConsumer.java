package com.tanveer.agent.infrastructure.messaging.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.agent.infrastructure.dto.OrderEventDto;
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
public class OrderEventConsumer extends AbstractEventConsumer<OrderEventDto> implements EventConsumer {

    public OrderEventConsumer(ObjectMapper objectMapper, EventHandlerRegistry<OrderEventDto> handlerRegistry) {
        super(objectMapper, handlerRegistry, OrderEventDto.class);
    }

    @Override
    @KafkaListener(topics = {"order-placed","order-canceled"}, groupId = "agent-01")
    public void consume(String message, Acknowledgment ack) throws AgentException, JsonProcessingException {
        process(message);
        log.info("Acknowledging the message processed safely");
        ack.acknowledge();
    }
}
