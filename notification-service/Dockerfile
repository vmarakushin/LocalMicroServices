FROM eclipse-temurin:17-jdk
LABEL authors="vmarakushin"
WORKDIR /app
COPY target/notification-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 12346
ENTRYPOINT ["java", "-jar", "app.jar"]