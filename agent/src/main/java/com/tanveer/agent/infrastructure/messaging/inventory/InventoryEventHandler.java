package com.tanveer.agent.infrastructure.messaging.inventory;

import com.tanveer.agent.application.RAGService;
import com.tanveer.agent.infrastructure.dto.EventType;
import com.tanveer.agent.infrastructure.dto.InventoryEventDto;
import com.tanveer.agent.infrastructure.exception.AgentException;
import com.tanveer.agent.infrastructure.messaging.EventHandler;
import com.tanveer.agent.infrastructure.messaging.product.InventoryContentBuilder;
import com.tanveer.agent.infrastructure.service.EmbeddingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryEventHandler implements EventHandler<InventoryEventDto> {
    private final RAGService ragService;
    private final InventoryContentBuilder inventoryContentBuilder;
    private final EmbeddingService embeddingService;

    @Override
    public EventType getSupportedEventType() {
        return EventType.INVENTORY_ADJUST;
    }

    @Override
    public void handler(InventoryEventDto event) throws AgentException {
        log.info("Event is {} ", event);
        String content = inventoryContentBuilder.buildProductText(event);

        // 2. Create metadata for filtering/querying
        Map<String, Object> metadata = Map.of(
                "content",content,
                "entity_type", event.aggregateType(),
                "event", event.eventType()
        );
        Document doc = new Document(
                content,                // content used for embedding
                metadata                // metadata JSON
        );

        try {
            log.info("Storing the document into vector db");
            ragService.store(List.of(doc));
        } catch (Exception e) {
            throw new AgentException("Document doesn't store into vector db");
        }
    }
}
