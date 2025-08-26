package org.tanveer.orderservice.domain.dto;

import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class OrderRequestDto {
    String customerId;
    List<OrderItemDto> items;


    @Data
    public static class OrderItemDto {
        private String productId;
        private String name;
        private String sku;
        private long price;
        private int quantity;
    }
}