package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;

public interface ProductEventHandler {
    public void handleProductCreated(ProductEventDto event);
    public void handleProductUpdated(ProductEventDto event);
    public void handleProductReleased(ProductEventDto event);
}
