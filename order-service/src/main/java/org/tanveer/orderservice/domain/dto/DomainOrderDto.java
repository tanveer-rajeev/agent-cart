package org.tanveer.orderservice.domain.dto;

import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class DomainOrderDto {
    UUID customerId;
    List<OrderItemDto> items;


    @Data
    public static class OrderItemDto {
        private UUID productId;
        private String name;
        private String sku;
        private long price;
        private int quantity;
    }
}