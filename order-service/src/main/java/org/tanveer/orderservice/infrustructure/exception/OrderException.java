package org.tanveer.orderservice.infrustructure.exception;

import org.tanveer.orderservice.infrustructure.dto.AvailableProductList;

public class OrderException extends RuntimeException {

    private final AvailableProductList availableProductResponseDto;

    public OrderException (String message, AvailableProductList availableProductResponseDto1){
        super(message);
        this.availableProductResponseDto = availableProductResponseDto1;
    }

    public AvailableProductList getOrderResponse() {
        return availableProductResponseDto;
    }
}
