package org.tanveer.orderservice.infrastructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityResponseDto;
import org.tanveer.orderservice.infrastructure.dto.ItemAvailabilityRequestDto;

@FeignClient(name = "inventory-client", url = "${application.services.inventory.url}")
public interface InventoryClient {

    @PostMapping("/availability")
    ItemAvailabilityResponseDto checkProductsAvailability(@RequestBody ItemAvailabilityRequestDto itemAvailabilityRequestDto);
}
