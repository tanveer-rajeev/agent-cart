package com.tanveer.agent.infrastructure.messaging;

import com.tanveer.agent.infrastructure.dto.DomainEventDto;
import com.tanveer.agent.infrastructure.dto.EventType;
import com.tanveer.agent.infrastructure.exception.AgentException;

public interface EventHandler<T extends DomainEventDto> {
    EventType getSupportedEventType();
    void handler(T event) throws AgentException;
}
