package com.tanveer.inventoryservice.application;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.mapper.InventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl{

    private final InventoryRepository inventoryRepository;
    private final EventRepository<InventoryEvent> eventRepository;

    public InventoryResponseDto getInventoryBySku(String sku) {
        return InventoryMapper.toResponseDto(inventoryRepository.findBySku(sku));
    }

    public InventoryResponseDto createInventory(InventoryRequestDto request) {
        log.info("Creating inventory {}",request.correlationId());
        Inventory inventory = inventoryRepository.save(Inventory.create(
                UUID.randomUUID(),
                request.correlationId(),
                request.sku(),
                request.availableQty(),
                request.reserveQty()
        ));
        publishEvents(inventory);
        return InventoryMapper.toResponseDto(inventory);
    }

    public InventoryResponseDto reserveStock(String sku, int quantity) {
        return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.reserve(quantity)));
    }

    public InventoryResponseDto releaseStock(String sku, int quantity) {
        return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.release(quantity)));
    }

    public InventoryResponseDto adjustStock(String sku, int quantity) {
        return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.adjust(quantity)));
    }

    private Inventory executeInventoryAction(String sku, Function<Inventory, Inventory> action) {

        Inventory inventory = inventoryRepository.findBySku(sku);

        Inventory updatedInventory = action.apply(inventory);

        log.info("Inventory is saving {}", inventory);

        inventoryRepository.save(updatedInventory);

        log.info("Inventory outbox event saving {}", inventory);

        publishEvents(updatedInventory);

        return updatedInventory;
    }

    public Inventory updateInventory(Inventory inventory) {
        log.info("updateInventory in action {}", inventory);
        return inventory;
    }

    private void publishEvents(Inventory inventory) {
        inventory.pullDomainEvents().forEach(eventRepository::saveEvent);
    }
}
