# ==============================
# 1. Build Stage
# ==============================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml and download dependencies (caches layers)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# ==============================
# 2. Runtime Stage
# ==============================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/webliix-backend-1.0.0.jar app.jar

# Expose port (Render will override with $PORT)
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
