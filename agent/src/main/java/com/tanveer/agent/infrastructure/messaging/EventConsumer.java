package com.tanveer.agent.infrastructure.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tanveer.agent.infrastructure.exception.AgentException;
import org.springframework.kafka.support.Acknowledgment;

public interface EventConsumer {
    void consume(String message, Acknowledgment ack) throws AgentException, JsonProcessingException;
}
