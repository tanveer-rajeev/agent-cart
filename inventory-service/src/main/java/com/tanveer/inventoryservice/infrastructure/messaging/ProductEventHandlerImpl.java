package com.tanveer.inventoryservice.infrastructure.messaging;

import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ProductEventDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import com.tanveer.inventoryservice.infrastructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEventHandlerImpl implements ProductEventHandler {

    private final InventoryService inventoryService;

    @Override
    public void handleProductCreated(ProductEventDto event) {
        inventoryService.createInventory(InventoryMapper.dtoToDomain(
                new InventoryRequestDto(event.aggregateId(), event.sku(),
                        0, 0,0)));
    }

    @Override
    public void handleProductUpdated(ProductEventDto event) throws InventoryException {
        inventoryService.adjustStock(event.sku(), event.quantity());
    }
}
