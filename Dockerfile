# Use an argument to accept the version during build
ARG APP_VERSION=latest
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
# Set an environment variable inside the container with the build argument value
ENV APP_VERSION=${APP_VERSION}
COPY target/*.jar api-teste.jar
ENTRYPOINT ["java", "-jar", "api-teste.jar"]


