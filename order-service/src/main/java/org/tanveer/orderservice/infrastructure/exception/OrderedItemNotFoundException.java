package org.tanveer.orderservice.infrastructure.exception;

import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityDto;

public class OrderedItemNotFoundException extends RuntimeException {

    private final ItemAvailabilityDto availableProductResponseDto;

    public OrderedItemNotFoundException(String message, ItemAvailabilityDto availableProductResponseDto1){
        super(message);
        this.availableProductResponseDto = availableProductResponseDto1;
    }

    public ItemAvailabilityDto getOrderResponse() {
        return availableProductResponseDto;
    }
}
