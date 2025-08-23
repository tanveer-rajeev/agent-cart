package com.tanveer.inventoryservice.infrustructure.messaging;

import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.ProductEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductEventHandler {

    private final InventoryService inventoryService;

    public void handleProductCreated(ProductEventDto event) {
        log.info("========Handling product event========");
        inventoryService.createInventory(new InventoryRequestDto(event.aggregateId(), event.sku(),
                0, 0));
    }

    public void handleProductUpdated(ProductEventDto event) {
        inventoryService.adjustStock(event.sku(), event.quantity());
    }

    public void handleProductReleased(ProductEventDto event) {
        inventoryService.releaseStock(event.sku(), event.quantity());
    }
}
