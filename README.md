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

- JPA/Hibernate with PostgresSQL (one database per service)

- JUnit 5, Mockito, AssertJ

- Docker

# How to Run

## Prerequisites
- Java 21+
- Maven 3.9+
- Docker
- Kafka (if running without Docker, install and start locally)

## Run Locally (without Docker)
- Clone the repository:
    ```bash
    git clone https://github.com/tanveer-rajeev/agent-cart.git
    cd agent-cart
    ```

## Start supporting services:

- Start Kafka on localhost:9092

- Start Postgres for each service (or update application.yml with your DB credentials)

    - Run each service:
      ```bash
      cd <service-name>
      mvn spring-boot:run
      ```
    - Or package and run the JAR:
      ```bash
      mvn clean package
      java -jar target/<service-name>-0.0.1-SNAPSHOT.jar
      ```
    - By default:

      - Eureka Server → http://localhost:8761

      - Config Server → http://localhost:8888

      - API Gateway → http://localhost:8989
      
# Service & API overview

## service-registry

- Provides service discovery using Netflix Eureka Server.
  
- Allows services to register themselves and discover other services dynamically.

## cloud-config-server
- ***Configuration***
    ```
    spring:
      config:
        import: "optional:configserver:http://localhost:8888"
    ```
- Centralized configuration management for all microservices.

- Stores common configuration in GitHub.

- Each service reads the configuration from the config server.

## api-gateway
- ***Configuration***
    ```
      spring:
        application:
          name: GATEWAY-SERVICE
        cloud:
         gateway:
           server:
             webflux:
               routes:
                 - id: order-service
                   uri: lb://ORDER-SERVICE
                   predicates:
                     - Path=/api/v1/orders/**
                   filters:
                     - name: Authentication
                     - name: CircuitBreaker
                       args:
                         name: orderCircuitBreaker
                         fallbackUri: forward:/orderFallBack
    ```
- Acts as a single entry point for client requests.

- Handles routing, JWT Authentication, and forwards requests to the appropriate service.
  
## auth-service

- JWT-based authentication and token validation

### SignUP
- **Signup Endpoint**: `POST http://localhost:8989/api/v1/auth/signup`
   - ***Request Body***
     ```json
     {
        "name": "user",
        "email": "user@example.com",
        "password": "Password123"
     }
     ```
         
  - ***Response***

    ```json
    {
        "id": "123",
        "name": "user",
        "email": "user@example.com",
        "createdAt": "2025-09-18T12:34:56"
    }
    ```
### Login
- **Endpoint**: `POST http://localhost:8989/api/v1/auth/login` 
    - ***Request Body***
      ```json
      {
        "email": "user@example.com",
        "password": "Password123"
      }
      ```
    - ***Response***
    ```json
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
    }
    ```
## API Documentation

Interactive API documentation is available via Swagger UI:

[Auth Service Open API Docs](http://localhost:9090/swagger-ui/index.html)

## product-service
### Add Product
- ***Endpoint***: `POST http://localhost:8989/api/v1/products`
  - ***Request Body***
    ```json
    {
        "name":"iPhone 8",
        "description":"manufacture by iPhone 8",
        "sku": "MOB-IPHONE-8",
        "price": 80000
    }
    ```

  - ***Response***
    ```json
    {
        "id": "35646ac3-a642-4ccb-bfd2-27daf951ca38",
        "sku": "MOB-IPHONE-8",
        "name": "iPhone 8",
        "description": "manufacture by iPhone 8",
        "price": 80000
    }
    ```

- Owns product catalog (name, price, SKU)

- Emits outbox events for inventory.created event

- Exposes CRUD APIs for products

## API Documentation

Interactive API documentation is available via Swagger UI:

[Product Service Open API Docs](http://localhost:9091/swagger-ui/index.html)

### Inventory API

#### Adjust Inventory
- **Endpoint**: `POST http://localhost:8989/api/v1/inventories/adjust/{sku}/{quantity}`

  - **Request Body**:
  ```json
  {
      "sku": "MOB-IPHONE-14",
      "availableQty": 7,
      "reserveQty": 0
  }
  ```

#### Reserve Inventory
- **Endpoint**: `POST http://localhost:8989/api/v1/inventories/reverse/MOB-IPHONE14/2`

  - **Request Body**:
    ```json
    {
        "sku": "MOB-IPHONE-14",
        "availableQty": 7,
        "reserveQty": 2
    }
    ```

#### Check Product Availability
- **Endpoint**: `POST http://localhost:8989/api/v1/inventories/availability`
    - ***Request Body***
      ```json
      {
          "orderItemList":[
              {
                  "sku":"MOB-IPHONE-8",
                  "quantity":3
              },
              {
                  "sku":"MOB-IPHONE-14",
                  "quantity":2
              }
          ]
      }
      ```
    - ***Response*** 
      ```json
      {
          "itemAvailabilityDto":[
              {
                  "sku": "MOB-IPHONE-8",
                  "requestedQty": 3,
                  "availableQty": 15,
                  "isAvailable": true
              },
              {
                  "sku": "MOB-IPHONE-14",
                  "requestedQty": 2,
                  "availableQty": 7,
                  "isAvailable": true
              }
          ]
      }
      ```
      
- Tracks stock levels per SKU

- Consumes inventory.created an event to create an initial stock record, which is 0 at first entry.

- Adds stock by api

- Consumes order.placed and produces inventory.reserved or inventory.release event.

## API Documentation

Interactive API documentation is available via Swagger UI:

[Inventory Service Open API Docs](http://localhost:9093/swagger-ui/index.html)

## order-service
### Place Order
- ***EndPoint***: `POST http://localhost:8989/api/v1/inventories/orders`
    - ***Request Body***
    ```json
        {
          "customerId": "1a0eb5b7-3ad4-4b37-86c6-e5c578e4a2c0",
          "items":[
            {
              "productId":"4dde7f7b-7a98-4a03-9a4f-ab051609b3f5",
              "name":"iPhone 8",
              "price":80000,
              "sku":"MOB-iPHONE-8",
              "quantity":5
            }
          ]
        }  
    ```
  
    - ***Response***
    ```json
        {
            "orderId": "47b5cfe1-bc84-40eb-b607-645bb860195f",
            "status": "ORDER_PLACED",
            "totalPrice": 80000
        }
    ```
- Accepts orders and manages order state

- Emits outbox events for order.placed

- Publishes order.created, consumes inventory events, confirms or rejects

- Implements a circuit breaker (Resilience4j) to gracefully handle failures during order processing, triggering a fallback that marks the order as pending or canceled, and uses the Outbox pattern to reliably schedule and publish the corresponding order events.

## API Documentation

Interactive API documentation is available via Swagger UI:

[Order Service Open API Docs](http://localhost:9094/swagger-ui/index.html)

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





