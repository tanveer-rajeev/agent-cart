# ===== BUILD STAGE =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy both modules
COPY common-lib ./common-lib
COPY inventory-service ./inventory-service

RUN --mount=type=cache,target=/root/.m2 mvn -f common-lib/pom.xml clean install -DskipTests
RUN --mount=type=cache,target=/root/.m2 mvn -f inventory-service/pom.xml clean package -DskipTests

# ===== RUN STAGE =====
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=builder /build/inventory-service/target/inventory-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9093
ENTRYPOINT ["java", "-jar", "app.jar"]