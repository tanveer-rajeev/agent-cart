package org.tanveer.orderservice.infrastructure.messaging;

import com.tanveer.commonlib.domain.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tanveer.orderservice.infrastructure.persistence.OrderEventEntity;
import org.tanveer.orderservice.infrastructure.persistence.OrderEventJpaRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class OrderEventProcessor implements EventService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final OrderEventJpaRepository orderEventJpaRepository;

    @Override
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void publish() {
        List<OrderEventEntity> pendingEvents = orderEventJpaRepository.findByPublishedFalse();

        for (OrderEventEntity event: pendingEvents){
            kafkaTemplate.send(event.getEventType(), event.getAggregateId().toString(), event.getPayload());

            log.info("Sent {} event to topic {}", event.getEventType(), event.getEventType());

            event.markPublished();
            orderEventJpaRepository.saveAndFlush(event);
        }
    }
}
