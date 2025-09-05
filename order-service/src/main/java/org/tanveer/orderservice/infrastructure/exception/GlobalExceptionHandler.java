package org.tanveer.orderservice.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderedItemNotFoundException.class)
    public ResponseEntity<ItemAvailabilityDto> handlePaymentException(OrderedItemNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getOrderResponse());
    }
}
