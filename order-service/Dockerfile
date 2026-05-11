# ===== BUILD STAGE =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy both modules
COPY common-lib ./common-lib
COPY order-service ./order-service

RUN --mount=type=cache,target=/root/.m2 mvn -f common-lib/pom.xml clean install -DskipTests
RUN --mount=type=cache,target=/root/.m2 mvn -f order-service/pom.xml clean package -DskipTests

# ===== RUN STAGE =====
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /build/order-service/target/order-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9094
ENTRYPOINT ["java", "-jar", "app.jar"]