package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.domain.EventType;
import com.tanveer.inventoryservice.infrastructure.dto.DomainEventDto;
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
