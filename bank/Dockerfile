FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/bank-kafka-system.jar app.jar
CMD ["java", "-jar", "app.jar"]