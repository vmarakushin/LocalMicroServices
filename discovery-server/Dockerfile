FROM eclipse-temurin:17-jdk
LABEL authors="vmarakushin"
WORKDIR /app
COPY target/discovery-server-1.0-SNAPSHOT.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]