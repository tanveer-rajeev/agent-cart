package org.tanveer.orderservice.infrustructure.dto;

import lombok.Data;
import lombok.Value;

import java.util.List;

@Value
public class OrderRequestDto {
    String customerId;
    List<OrderItemDto> items;


    @Data
    public static class OrderItemDto {
        private String id;
        private String productId;
        private String name;
        private String sku;
        private long price;
        private int quantity;
    }
}