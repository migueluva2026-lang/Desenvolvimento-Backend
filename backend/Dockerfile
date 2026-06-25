# Backend Dockerfile for a Spring Boot application
FROM eclipse-temurin:17-jdk-jammy AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw clean package -DskipTests -B

EXPOSE 8080

CMD ["sh","-c","java -jar target/*.jar"]