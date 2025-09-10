# Agent-cart

A small, production-style e‑commerce system built with Spring Boot, Spring Cloud, and Apache Kafka. It follows Domain-Driven Design and Hexagonal architecture with clear bounded contexts and event-driven communication.

# Highlights

- Services: service-registry, config-server, api-gateway, auth-service, product-service, inventory-service, order-service

- Spring Cloud: Eureka discovery, Config Server, Gateway routing, circuit breaker

- Kafka: asynchronous inter-service events, idempotent consumers

- Outbox pattern: product-service emits inventory. created events, order-service emits order.placed events; scheduled job delivers events reliably

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

- Spring Cloud 2025.0.0 (Eureka, Config Server, Gateway)

- Apache Kafka and Spring for Apache Kafka

- Outbox + scheduler pattern for reliable event publishing

- JPA/Hibernate with MySQL (one database per service)

- JUnit 5, Mockito, AssertJ

- Docker

# Service overview

## service-registry

- Provides service discovery using Netflix Eureka Server.
  
- Allows services to register themselves and discover other services dynamically.

## cloud-config-server

- Centralized configuration management for all microservices.

- Stores common configuration in GitHub.

- Each service reads the configuration from the config server.

## api-gateway

- Acts as a single entry point for client requests.

- Handles routing, JWT Authentication, and forwards requests to the appropriate service.
  
## auth-service

- JWT-based authentication and token validation

- Endpoints: /auth/v1/register, /auth/v1/login, /auth/v1/validate

## product-service

- Owns product catalog (name, price, SKU)

- Emits outbox events for inventory.created event

- Exposes CRUD APIs for products

## inventory-service

- Tracks stock levels per SKU

- Consumes inventory.created an event to create an initial stock record, which is 0 at first entry.

- Adds stock by api

- Consumes order.placed and produces inventory.reserved or inventory.release event.

## order-service

- Accepts orders and manages order state

- Emits outbox events for order.placed

- Publishes order.created, consumes inventory events, confirms or rejects

- Implements a circuit breaker (Resilience4j) to gracefully handle failures during order processing, triggering a fallback that marks the order as pending or canceled, and uses the Outbox pattern to reliably schedule and publish the corresponding order events.


# Scalability & Event-Driven Design

 ## Kafka Configuration
 ```
 kafka:
    bootstrap-servers: localhost:9092
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer

      acks: all
      retries: 2147483647
      properties:
        enable.idempotence: true
        max.in.flight.requests.per.connection: 5
        compression.type: zstd
        linger.ms: 50
        batch.size: 65536
  consumer:
    key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
    value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
  listener:
    ack-mode: manual_immediate
    group-id: inventory-group
    enable-auto-commit: false 
    auto-offset-reset: earliest             
    properties:
      max.poll.records: 500                 
```


**This system is designed with Kafka-based event streaming to ensure horizontal scalability and high throughput. The architecture supports processing millions of events per day by leveraging the following practices:**

- Partitioned Topics

  - Each topic (e.g., order.placed, product.created) is created with 8+ partitions.

  - Partitions enable parallel consumption so we can scale horizontally by adding more consumers.
 
 - Consumer Groups

   - Each microservice runs in its own consumer group (e.g., inventory-group, analytics-group).

   - Adding new instances automatically balances partitions across consumers for higher throughput.

- Idempotent & Reliable Event Processing

  - Idempotent producers (acks=all, enable.idempotence=true) prevent duplicate events.

  - Consumers use manual offset commit — offsets are committed only after successful database transactions. ( disable auto-commit = false )

  - Processed-event table in each service ensures that replayed messages don’t cause duplicate updates.

- Outbox Pattern
  
  - Each service publishes events using an outbox table + scheduled job.

  - This ensures atomicity between database writes and event publishing.

  -  In production, this can be easily replaced with Debezium CDC for near-real-time streaming.

- Horizontal Scalability

  - To handle 1M+ order events, we can:

  - Increase partitions (more parallelism).

  - Add more consumer instances (automatic rebalancing).

  - Scale Kafka brokers if needed (cluster supports partition distribution).


## Sample of Outbox Pattern flow

<img width="1218" height="569" alt="Screenshot 2025-09-06 114545" src="https://github.com/user-attachments/assets/36cd02ad-945a-4007-b256-0ef27c186b6d" />



## High-level Design & Future Improvement Plan

- AI Integration – Planned integration of Spring AI or LangChain4j with Ollama to implement an agent-driven shopping cart. (In progress)
  
- Payment & Notification Services – Development is pending and will be completed in upcoming iterations.

  
<img width="1364" height="807" alt="high level desgin" src="https://github.com/user-attachments/assets/a05f0056-6a0a-42e9-b1e6-4b779a2e734a" />





