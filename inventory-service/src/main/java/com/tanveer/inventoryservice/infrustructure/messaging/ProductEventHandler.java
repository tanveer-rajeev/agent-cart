package com.tanveer.inventoryservice.infrustructure.messaging;

import com.tanveer.inventoryservice.infrustructure.dto.ProductEventDto;

public interface ProductEventHandler {
    public void handleProductCreated(ProductEventDto event);
    public void handleProductUpdated(ProductEventDto event);
    public void handleProductReleased(ProductEventDto event);
}
