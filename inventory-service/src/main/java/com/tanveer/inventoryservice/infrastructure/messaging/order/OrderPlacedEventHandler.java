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
public class OrderPlacedEventHandler implements EventHandler<OrderEventDto> {

    private final InventoryService inventoryService;

    @Override
    public EventType getSupportedEventType() {
        return EventType.ORDER_PLACED;
    }

    @Override
    public void handler(OrderEventDto event) throws InventoryException {
        log.info("Handling ORDER_PLACED event");
        inventoryService.reserveStock(event.sku(), event.quantity());
    }
}
