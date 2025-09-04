# Agent-cart

A small, production-style e‑commerce system built with Spring Boot, Spring Cloud, and Apache Kafka. It follows Domain-Driven Design and Hexagonal architecture with clear bounded contexts and event-driven communication.

# Highlights

- Services: service-registry, config-server, api-gateway, auth-service, product-service, inventory-service, order-service

- Spring Cloud: Eureka discovery, Config Server, Gateway routing, circuit breaker

- Kafka: asynchronous inter-service events, idempotent consumers

- Outbox pattern: product-service emits inventory.created events, order-service emits order.placed events; scheduled job delivers events reliably

- DDD + Hexagonal: domain-centric code, adapters for api, persistence, and messaging

# Domain and package layout (Hexagonal)
```text
<service-name>
├─ src/main/java
│ └─ com.tanveer.<service>
│ ├─ domain # Entities, value objects, domain services, events
│ ├─ application # Use cases, ports
│ ├─ infrastructure
│ │ ├─ api # REST controllers (inbound adapter)
│ │ ├─ persistence # JPA repositories, mappings (outbound)
│ │ └─ messaging # Kafka producers/consumers + outbox scheduler
│ │ └─ config # Spring configuration
│ 
└─ src/main/resources
```

## Tech stack

- Java 21, Spring Boot 3

- Spring Cloud 2023.x (Eureka, Config Server, Gateway)

- Apache Kafka and Spring for Apache Kafka

- Outbox + scheduler pattern for reliable event publishing

- JPA/Hibernate with MySQL (one database per service)

- JUnit 5, Mockito, AssertJ

- Docker

## Service overview
# auth-service

- JWT-based authentication and token validation

- Endpoints: /auth/register, /auth/login, /auth/validate

# product-service

- Owns product catalog (name, price, SKU)

- Emits outbox events for inventory.created

- Exposes CRUD APIs for products

# inventory-service

- Tracks stock levels per SKU

- Consumes inventory.created to create initial stock record

- Consumes order.placed and produces inventory.reserved or inventory.rejected

# order-service

- Accepts orders and manages order state

- Emits outbox events for order.placed

- Publishes order.created, consumes inventory events, confirms or rejects

- Implements a circuit breaker (Resilience4j) to gracefully handle failures during order processing, triggering a fallback that marks the order as pending or canceled, and uses the Outbox pattern to reliably schedule and publish the corresponding order events.


## High-level Design & Future Improvement Plan

- AI is not implemented yet. The plan is to integrate Spring AI or LangChain4j with Ollama to build a complete agent-driven cart. (Work in progress)
- Payment and Notification services are not yet completed.

  
<img width="1364" height="807" alt="high level desgin" src="https://github.com/user-attachments/assets/a05f0056-6a0a-42e9-b1e6-4b779a2e734a" />


