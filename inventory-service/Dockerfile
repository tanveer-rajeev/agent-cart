# ===== COMMON LIB BUILD STAGE =====
FROM maven:3.9.4-eclipse-temurin-17 AS common-lib-builder
WORKDIR /build/common-lib
COPY ../common-lib /build/common-lib
RUN mvn clean install -DskipTests

# ===== PRODUCT SERVICE BUILD STAGE =====
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY . .
COPY --from=common-lib-builder /root/.m2 /root/.m2
RUN mvn clean package

# ===== RUN STAGE =====
FROM openjdk:21-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/inventory-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4005

ENTRYPOINT ["java", "-jar", "app.jar"]