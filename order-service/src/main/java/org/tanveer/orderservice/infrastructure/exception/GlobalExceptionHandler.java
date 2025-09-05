package org.tanveer.orderservice.infrastructure.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityDto;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderedItemNotFoundException.class)
    public ResponseEntity<ItemAvailabilityDto> handleOrderedItemNotFound(OrderedItemNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getOrderResponse());
    }

    @ExceptionHandler(InventoryServiceUnavailableException.class)
    public ResponseEntity<ApiResponse> handleInventoryUnavailable(InventoryServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ApiResponse.builder().message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(BadInventoryRequestException.class)
    public ResponseEntity<ApiResponse> handleBadInventoryRequest(BadInventoryRequestException ex) {
        log.info("=======BadInventoryRequestException occurred=======");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.builder().message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiResponse> handleGenericFeign(FeignException ex) {
        log.info("======FeignException occurred======");
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                .body(ApiResponse.builder().message(ex.getMessage())
                        .build());
    }
}
