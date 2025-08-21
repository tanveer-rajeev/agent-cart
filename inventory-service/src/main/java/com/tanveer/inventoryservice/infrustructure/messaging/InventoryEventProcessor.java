package com.tanveer.inventoryservice.infrustructure.messaging;

import com.tanveer.commonlib.domain.EventService;
import com.tanveer.inventoryservice.infrustructure.persistence.EventJpaRepository;
import com.tanveer.inventoryservice.infrustructure.persistence.InventoryEventEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class InventoryEventProcessor implements EventService {

  private final EventJpaRepository repository;
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Transactional
  @Scheduled(fixedDelay = 5000)
  @Override
  public void publish() {
    log.info("EventProcessor running...");
    List<InventoryEventEntity> pendingEvents = repository.findByPublishedFalse();

    for (InventoryEventEntity pendingEvent : pendingEvents) {
      try {
        kafkaTemplate.send(
          pendingEvent.getEventType(),
          pendingEvent.getAggregateId().toString(),
          pendingEvent.getPayload()
        );
        pendingEvent.markPublished();
        repository.saveAndFlush(pendingEvent);
        log.info("Sent {} event for SKU {} of aggregate {}", pendingEvent.getEventType(), pendingEvent.getSku(),
          pendingEvent.getAggregateType());
      } catch (Exception e) {
        log.error("Failed to send event {} for SKU {}", pendingEvent.getEventType(), pendingEvent.getSku(), e);
      }
    }
  }
}
