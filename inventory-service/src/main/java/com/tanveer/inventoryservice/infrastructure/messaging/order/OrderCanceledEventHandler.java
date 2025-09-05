package com.tanveer.inventoryservice.infrastructure.messaging.order;

import com.tanveer.inventoryservice.domain.EventType;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.OrderEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.messaging.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCanceledEventHandler implements EventHandler<OrderEventDto> {
    private final InventoryService inventoryService;

    @Override
    public EventType getSupportedEventType() {
        return EventType.ORDER_CANCELED;
    }

    @Override
    public void handler(OrderEventDto event) throws InventoryException {
        log.info("Handling ORDER_CANCELED event");
        inventoryService.releaseStock(event.sku(), event.quantity());
    }
}
