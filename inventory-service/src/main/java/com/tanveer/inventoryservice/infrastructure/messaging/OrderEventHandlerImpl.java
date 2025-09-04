package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.OrderEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventHandlerImpl implements OrderEventHandler {

    private final InventoryService inventoryService;

    @Override
    public void handleOrderCanceled(OrderEventDto event) throws InventoryException {
        inventoryService.releaseStock(event.sku(), event.quantity());
    }

    @Override
    public void handleOrderPlaced(OrderEventDto event) throws InventoryException {
        inventoryService.reserveStock(event.sku(), event.quantity());
    }
}
