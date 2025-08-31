package com.tanveer.productservice.infrastructure.messaging;

import com.tanveer.commonlib.domain.EventService;
import com.tanveer.productservice.infrastructure.persistence.ProductEventEntity;
import com.tanveer.productservice.infrastructure.config.KafkaTopicConfiguration;
import com.tanveer.productservice.infrastructure.persistence.ProductEventJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductEventProcessor implements EventService {

  private final ProductEventJpaRepository eventRepository;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final KafkaTopicConfiguration kafkaTopicConfiguration;

  @Transactional
  @Scheduled(fixedDelay = 5000)
  @Override
  public void publish() {
    log.info("OutboxProcessor running...");
    List<ProductEventEntity> pending = eventRepository.findByPublishedFalse();

    for (ProductEventEntity event : pending) {
      try {
        String eventType = event.getEventType();
        String topic = kafkaTopicConfiguration.getMap().get(eventType);

        if (topic == null) {
          log.warn("No topic configured for event type {}", eventType);
        } else {
          kafkaTemplate.send(topic, event.getAggregateId().toString(), event.getPayload());
          log.info("Sent {} event to topic {}", eventType, topic);
        }

        event.markPublished();
        eventRepository.saveAndFlush(event);
        log.info("Sent {} event for SKU {} of aggregate {}", event.getEventType(), event.getSku(), event.getAggregateType());
      } catch (Exception e) {
        log.error("Failed to send event {} for SKU {}", event.getEventType(), event.getSku(), e);
      }
    }
  }
}
