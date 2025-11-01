package com.tanveer.agent.infrastructure.messaging;

import com.tanveer.agent.infrastructure.dto.DomainEventDto;
import com.tanveer.agent.infrastructure.dto.EventType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class EventHandlerRegistry<T extends DomainEventDto> {
    Map<EventType, EventHandler<T>> map = new EnumMap<>(EventType.class);

    public EventHandlerRegistry(List<EventHandler<T>> handlers) {
        for (EventHandler<T> handler : handlers) {
            map.put(handler.getSupportedEventType(), handler);
        }
    }

    public EventHandler<T> getHandler(EventType eventType) {
        return map.get(eventType);
    }
}
