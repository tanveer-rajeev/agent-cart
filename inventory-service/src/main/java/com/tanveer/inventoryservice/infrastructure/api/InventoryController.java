package com.tanveer.inventoryservice.infrastructure.api;

import com.tanveer.inventoryservice.application.InventoryUseCase;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.InventoryResponseDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityRequestDto;
import com.tanveer.inventoryservice.infrastructure.dto.ItemAvailabilityResponseDto;
import com.tanveer.inventoryservice.infrastructure.exception.InventoryException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventories")
@Tag(name = "Inventory", description = "Inventory service APIs")
public class InventoryController {

    private final InventoryUseCase inventoryService;

    @GetMapping("/{sku}")
    @Operation(summary = "Get inventory by SKU", description = "Retrieve inventory details by SKU")
    public InventoryResponseDto getBySku(@PathVariable String sku) throws InventoryException {
        return inventoryService.getInventoryBySku(sku);
    }

    @PostMapping("/availability")
    @Operation(summary = "Check products availability", description = "Check availability of multiple products")
    public ItemAvailabilityResponseDto checkProductsAvailability(
            @RequestBody ItemAvailabilityRequestDto itemAvailabilityRequestDto) throws InventoryException {
        return inventoryService.checkProductsAvailability(itemAvailabilityRequestDto);
    }

    @PostMapping
    @Operation(summary = "Create inventory", description = "Create a new inventory record")
    public ResponseEntity<InventoryResponseDto> createInventory(@RequestBody InventoryRequestDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.createInventory(request));
    }

    @PostMapping("/adjust/{sku}/{quantity}")
    @Operation(summary = "Adjust stock", description = "Adjust stock for a specific SKU")
    public ResponseEntity<InventoryResponseDto> adjustStock(@PathVariable String sku, @PathVariable Integer quantity)
            throws InventoryException {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.adjustStock(sku, quantity));
    }

    @PostMapping("/release/{sku}/{quantity}")
    @Operation(summary = "Release stock", description = "Release stock for a specific SKU")
    public ResponseEntity<InventoryResponseDto> releaseStock(@PathVariable String sku, @PathVariable Integer quantity)
            throws InventoryException {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.releaseStock(sku, quantity));
    }

    @PostMapping("/reverse/{sku}/{quantity}")
    @Operation(summary = "Reverse stock", description = "Reverse stock for a specific SKU")
    public ResponseEntity<InventoryResponseDto> reserveStock(@PathVariable String sku, @PathVariable Integer quantity)
            throws InventoryException {
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.reserveStock(sku, quantity));
    }
}
