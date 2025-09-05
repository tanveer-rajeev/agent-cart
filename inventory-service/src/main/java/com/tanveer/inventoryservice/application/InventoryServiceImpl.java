package com.tanveer.inventoryservice.application;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.domain.*;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.dto.OrderItem;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final EventRepository<InventoryEvent> eventRepository;

    @Override
    public Inventory reserveStock(String sku, int quantity) throws InventoryException {
        return executeInventoryAction(sku,inventory -> inventory.reserve(quantity));
    }

    @Override
    public Inventory releaseStock(String sku, int quantity) throws InventoryException {
        return executeInventoryAction(sku,inventory -> inventory.release(quantity));
    }

    @Override
    public Inventory adjustStock(String sku, int quantity) throws InventoryException {
        return executeInventoryAction(sku,inventory -> inventory.adjust(quantity));
    }

    @Override
    public Inventory getInventoryBySku(String sku) throws InventoryException {
        return inventoryRepository.findBySku(sku);
    }

    @Override
    public ItemAvailabilityResponseDto checkProductsAvailability(ItemAvailabilityRequestDto itemAvailabilityRequestDto) throws InventoryException {
        List<ItemAvailabilityDto> list = new ArrayList<>();

        for (OrderItem item : itemAvailabilityRequestDto.orderItemList()) {
            Inventory inventory = inventoryRepository.findBySku(item.sku());
            boolean isAvailable = inventory.getAvailableQty() >= item.quantity();
            list.add(new ItemAvailabilityDto(item.sku(), item.quantity(), inventory.getAvailableQty(), isAvailable));
        }
        return new ItemAvailabilityResponseDto(list);
    }

    @Override
    public Inventory createInventory(Inventory inventory) {
        log.info("Creating inventory {}", inventory.getProductId());
        inventory = Inventory.create(
                inventory.getProductId(),
                inventory.getSku(),
                inventory.getAvailableQty(),
                inventory.getReservedQty(),
                inventory.getVersion()
        );
        inventoryRepository.save(inventory);
        saveEvents(inventory);
        return inventory;
    }

    @Override
    public Inventory updateInventory(Inventory inventory) {
        return null;
    }

    private Inventory executeInventoryAction(String sku, InventoryAction action) throws InventoryException {

        Inventory inventory = inventoryRepository.findBySku(sku);

        Inventory updatedInventory = action.apply(inventory);

        log.info("Inventory is saving {}", inventory);

        inventoryRepository.update(updatedInventory);

        log.info("Inventory outbox event saving {}", inventory);

        saveEvents(updatedInventory);

        return updatedInventory;
    }

    private void saveEvents(Inventory inventory) {
        inventory.pullDomainEvents().forEach(eventRepository::saveEvent);
    }
}
