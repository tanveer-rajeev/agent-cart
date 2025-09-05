package org.tanveer.orderservice.infrastructure.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class InventoryClientErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultDecoder = new Default();

    private final Map<Integer, Function<Response, Exception>> exceptionMapping = Map.of(
            400, r -> new BadInventoryRequestException("Invalid request sent to inventory service."),
            503, r -> new InventoryServiceUnavailableException("Inventory service is unavailable.")
    );

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Handle feign client error by decoder");
        return exceptionMapping
                .getOrDefault(response.status(), r -> defaultDecoder.decode(methodKey, r))
                .apply(response);
    }
}
