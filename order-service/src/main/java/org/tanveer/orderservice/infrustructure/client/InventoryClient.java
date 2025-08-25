package org.tanveer.orderservice.infrustructure.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.tanveer.orderservice.infrustructure.dto.AvailableProductResponseDto;
import org.tanveer.orderservice.infrustructure.dto.ProductRequestDto;

@FeignClient(name = "inventory-client", url = "${application.services.inventory.url}")
public interface InventoryClient {

    @GetMapping("/availability")
    public AvailableProductResponseDto checkProductsAvailability(@RequestBody ProductRequestDto productRequestDto);
}
