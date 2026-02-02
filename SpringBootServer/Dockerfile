# Build
FROM eclipse-temurin:23-jdk AS build
WORKDIR /app

# Configuration
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Permission
RUN chmod +x gradlew

COPY src src

# Compilation
RUN ./gradlew bootJar -x test --no-daemon

# Runtime
FROM eclipse-temurin:23-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]