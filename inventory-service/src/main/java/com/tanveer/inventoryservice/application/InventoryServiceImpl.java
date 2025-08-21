package com.tanveer.inventoryservice.application;

import com.tanveer.commonlib.domain.EventRepository;
import com.tanveer.inventoryservice.domain.Inventory;
import com.tanveer.inventoryservice.domain.InventoryEvent;
import com.tanveer.inventoryservice.domain.InventoryRepository;
import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.mapper.InventoryMapper;

import java.util.Optional;
import java.util.function.Function;

public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;
  private final EventRepository<InventoryEvent> eventRepository;

  public InventoryServiceImpl(InventoryRepository inventoryRepository,
                              EventRepository<InventoryEvent> eventRepository) {
    this.inventoryRepository = inventoryRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  public InventoryResponseDto getInventoryBySku(String sku) {
    return InventoryMapper.toResponseDto(inventoryRepository.findBySku(sku));
  }

  @Override
  public InventoryResponseDto createInventory(Inventory request) {
    return InventoryMapper.toResponseDto(inventoryRepository.save(request));
  }

  @Override
  public InventoryResponseDto reserveStock(String sku, int quantity) {
    return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.reserve(quantity)));
  }

  @Override
  public InventoryResponseDto releaseStock(String sku, int quantity) {
    return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.release(quantity)));
  }

  @Override
  public InventoryResponseDto adjustStock(String sku, int quantity) {
    return InventoryMapper.toResponseDto(executeInventoryAction(sku, inventory -> inventory.adjust(quantity)));
  }

  private Inventory executeInventoryAction(String sku, Function<Inventory, Inventory> action) {

    Inventory inventory = inventoryRepository.findBySku(sku);

    Inventory updatedInventory = action.apply(inventory);

    inventoryRepository.save(updatedInventory);

    updatedInventory.pullDomainEvents().forEach(eventRepository::saveEvent);

    return updatedInventory;
  }
}
// Inventory consume product with sku and quantity
// if product already exist then it will update inventory
// if not then create new inventory with product sku and quantity