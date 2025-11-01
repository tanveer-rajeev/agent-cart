package com.tanveer.agent.infrastructure.messaging.order;

import com.tanveer.agent.infrastructure.dto.OrderEventDto;
import org.springframework.stereotype.Service;

@Service
public class OrderContentBuilder {
    StringBuilder content = new StringBuilder();

    public String builder(OrderEventDto eventDto) {
        return content.append("The order has been ")
                .append(eventDto.eventType().toString().split("_")[0])
                .append(" for the ")
                .append(eventDto.productName()).append(" product")
                .append(" with ").append(eventDto.quantity())
                .append(" quantity ")
                .append(" on ")
                .append(eventDto.occurredAt()).toString();

    }
}
