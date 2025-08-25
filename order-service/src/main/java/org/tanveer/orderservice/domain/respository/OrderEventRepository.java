package org.tanveer.orderservice.domain.respository;

import com.tanveer.commonlib.domain.EventRepository;
import org.tanveer.orderservice.domain.model.OrderEvent;

public interface OrderEventRepository extends EventRepository<OrderEvent> { }
