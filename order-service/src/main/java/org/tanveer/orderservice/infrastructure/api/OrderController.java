package org.tanveer.orderservice.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tanveer.orderservice.application.OrderUseCase;
import org.tanveer.orderservice.infrastructure.dto.OrderRequestDto;
import org.tanveer.orderservice.infrastructure.dto.OrderResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderUseCase orderUseCase;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        return ResponseEntity.ok().body(orderUseCase.create(orderRequestDto));
    }
}
