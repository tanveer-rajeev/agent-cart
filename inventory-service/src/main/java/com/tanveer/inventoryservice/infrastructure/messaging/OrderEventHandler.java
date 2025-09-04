package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.infrastructure.dto.OrderEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface OrderEventHandler {
    void handleOrderCanceled(OrderEventDto event) throws InventoryException;
    void handleOrderPlaced(OrderEventDto event) throws InventoryException;
}
