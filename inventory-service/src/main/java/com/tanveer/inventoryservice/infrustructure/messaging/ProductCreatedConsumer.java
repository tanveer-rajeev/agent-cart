package com.tanveer.inventoryservice.infrustructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.ProductCreatedEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreatedConsumer {
  private final ObjectMapper objectMapper;
  private final InventoryService inventoryService;

  @KafkaListener(topics = "product-created", groupId = "inventory-service-01")
  public void consume(String message) {
    try {
      log.info("Message: {}", message);

      ProductCreatedEventDto event = objectMapper.readValue(message, ProductCreatedEventDto.class);
      log.info("Received ProductCreated event: {}", event);

      inventoryService.adjustStock(event.sku(), event.quantity());

    } catch (Exception e) {
      log.error("Error processing ProductCreated event", e);
    }
  }
}
