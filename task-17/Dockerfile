FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY app/target/app.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
