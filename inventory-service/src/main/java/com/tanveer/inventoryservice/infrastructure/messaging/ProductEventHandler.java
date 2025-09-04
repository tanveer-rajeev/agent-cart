package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;

public interface ProductEventHandler {
    void handleProductCreated(ProductEventDto event);
    void handleProductUpdated(ProductEventDto event) throws InventoryException;

}
