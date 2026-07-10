# Use an argument to accept the version during build
ARG APP_VERSION=latest
FROM eclipse-temurin:17-jdk-jammy
ARG APP_VERSION
WORKDIR /app
# Copy the JAR file built with the specified version
COPY target/api-teste-${APP_VERSION}.jar api-teste.jar
ENTRYPOINT ["java", "-jar", "api-teste.jar"]


