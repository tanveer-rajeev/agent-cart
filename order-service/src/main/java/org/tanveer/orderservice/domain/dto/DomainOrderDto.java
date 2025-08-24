package org.tanveer.orderservice.domain.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

public class DomainOrderDto {
    private UUID customerId;
    private List<OrderItemDto> items;

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Data
    public static class OrderItemDto {
        private UUID productId;
        private String name;
        private String sku;
        private long price;
        private int quantity;
    }
}