package com.tanveer.inventoryservice.infrastructure.messaging.product;

import com.tanveer.inventoryservice.domain.EventType;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.messaging.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryUpdatedEventHandler implements EventHandler<ProductEventDto> {
    private final InventoryService inventoryService;

    @Override
    public EventType getSupportedEventType() {
        return EventType.PRODUCT_UPDATED;
    }

    @Override
    public void handler(ProductEventDto event) throws InventoryException {
        inventoryService.adjustStock(event.sku(), event.quantity());
    }
}
