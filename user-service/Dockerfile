FROM eclipse-temurin:17-jdk
LABEL authors="vmarakushin"
WORKDIR /app
COPY target/user-service-1.0-SNAPSHOT.jar app.jar
EXPOSE 12345
ENTRYPOINT ["java", "-jar", "app.jar"]