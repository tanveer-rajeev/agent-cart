package com.tanveer.inventoryservice.infrastructure.messaging.product;

import com.tanveer.inventoryservice.domain.EventType;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.mapper.InventoryMapper;
import com.tanveer.inventoryservice.infrastructure.messaging.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryCreatedEventHandler implements EventHandler<ProductEventDto> {

    private final InventoryService inventoryService;

    @Override
    public EventType getSupportedEventType() {
        return EventType.PRODUCT_CREATED;
    }

    @Override
    public void handler(ProductEventDto event) {
        log.info("Product created event is : {}",event);
        inventoryService.createInventory(
                InventoryMapper.dtoToDomain(
                        new InventoryRequestDto(event.aggregateId(), event.name(),event.description(),event.price(),
                                event.sku(), 0, 0, 0)
                )
        );
    }
}
