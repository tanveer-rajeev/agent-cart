package com.tanveer.agent.infrastructure.messaging.product;

import com.tanveer.agent.infrastructure.dto.InventoryEventDto;
import org.springframework.stereotype.Service;

@Service
public class InventoryContentBuilder {

    public String buildProductText(InventoryEventDto event) {
        StringBuilder sb = new StringBuilder();

        sb.append("Product Information:\n");
        sb.append("The product '")
                .append(event.name())
                .append("' is a ")
                .append(event.sku().split("-")[0])
                .append(" item ")
                .append(". ");

        if (event.description() != null && !event.description().isEmpty()) {
            sb.append("Description: ").append(event.description()).append(". ");
        }

        sb.append("It is priced at ").append(event.price()).append(" USD. ");
        sb.append("Created").append(" on ").append(event.occurredAt()).append(".");

        return sb.toString();
    }
}
