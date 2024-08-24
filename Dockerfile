# Dockerfile for Spring Boot backend
FROM openjdk:17-jdk

COPY target/backend.jar /app/backend.jar

WORKDIR /app

EXPOSE 8080
CMD ["java", "-jar", "backend.jar","--server.address=0.0.0.0"]
