package com.tanveer.agent.infrastructure.messaging.order;

import com.tanveer.agent.application.RAGService;
import com.tanveer.agent.application.RAGUseCase;
import com.tanveer.agent.infrastructure.dto.EventType;
import com.tanveer.agent.infrastructure.dto.OrderEventDto;
import com.tanveer.agent.infrastructure.exception.AgentException;
import com.tanveer.agent.infrastructure.messaging.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderPlacedEventHandler implements EventHandler<OrderEventDto> {

    private final RAGUseCase ragUseCase;
    private final OrderContentBuilder contentBuilder;

    @Override
    public EventType getSupportedEventType() {
        return EventType.ORDER_PLACED;
    }

    @Override
    public void handler(OrderEventDto event) {
        log.info("Handling ORDER event");
        String content = contentBuilder.builder(event);

        Map<String, Object> metadata = Map.of("event", event.eventType(),
                "category", event.sku().split("-")[0]);

//        ragUseCase.store(List.of(new Document(content, metadata)));
    }
}
