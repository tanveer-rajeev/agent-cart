package org.tanveer.orderservice.infrastructure.exception;

public class ErrorMessage {
    public static final String SERVICE_UNAVAILABLE =
            "Inventory service is temporarily unavailable. Order saved as PENDING.";
    public static final String ITEMS_UNAVAILABLE =
            "Some items are temporarily unavailable. Order saved as PENDING.";
}
