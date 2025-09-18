package org.tanveer.orderservice.infrastructure.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tanveer.orderservice.application.OrderUseCase;
import org.tanveer.orderservice.infrastructure.dto.OrderRequestDto;
import org.tanveer.orderservice.infrastructure.dto.OrderResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "Order service APIs")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    @Operation(summary = "Create Order", description = "Create a new order")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok().body(orderUseCase.create(orderRequestDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Order", description = "Update an existing order by ID")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable String id, @RequestBody OrderRequestDto orderRequestDto) {
        return ResponseEntity.ok().body(orderUseCase.update(orderRequestDto, id));
    }
}
