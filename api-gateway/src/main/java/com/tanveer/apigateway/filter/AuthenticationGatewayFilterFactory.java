package com.tanveer.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final RouteValidator validator;
    private final WebClient webClient;

    public AuthenticationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                              RouteValidator routeValidator,
                                              @Value("${auth.service.url}") String authServiceUrl) {

        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
        this.validator = routeValidator;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return ((exchange, chain) -> {
            log.info("Auth filter triggered for ");

            if (validator.isSecured.test(exchange.getRequest())) {
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

                if (authHeader != null && !authHeader.startsWith("Bearer ")) {
                    log.warn("Missing or invalid authorization header");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                return webClient.get()
                        .uri("/api/v1/auth/validate")
                        .header(HttpHeaders.AUTHORIZATION, authHeader)
                        .retrieve()
                        .toBodilessEntity()
                        .then(chain.filter(exchange))
                        .onErrorResume(e -> {
                            log.warn("Token validation failed: {}", e.getMessage());
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        });
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}

