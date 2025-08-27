package com.tanveer.inventoryservice.infrustructure.api;

import com.tanveer.inventoryservice.domain.InventoryService;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrustructure.dto.ItemAvailabilityResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    public InventoryResponseDto getBySku(@PathVariable String sku) {
        return inventoryService.getInventoryBySku(sku);
    }

    @PostMapping("/availability")
    public ItemAvailabilityResponseDto checkProductsAvailability(@RequestBody ItemAvailabilityRequestDto itemAvailabilityRequestDto) {
        return inventoryService.checkProductsAvailability(itemAvailabilityRequestDto);
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDto> createInventory(@RequestBody InventoryRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.createInventory(request));
    }

    @PostMapping("/adjust/{sku}/{quantity}")
    public ResponseEntity<InventoryResponseDto> adjustStock(@PathVariable String sku, @PathVariable Integer quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.adjustStock(sku, quantity));
    }

    @PostMapping("/release/{sku}/{quantity}")
    public ResponseEntity<InventoryResponseDto> releaseStock(@PathVariable String sku, @PathVariable Integer quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.releaseStock(sku, quantity));
    }

    @PostMapping("/reverse/{sku}/{quantity}")
    public ResponseEntity<InventoryResponseDto> reserveStock(@PathVariable String sku, @PathVariable Integer quantity) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.reserveStock(sku, quantity));
    }
}
