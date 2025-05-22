#FROM openjdk:17

FROM openjdk:25-slim

WORKDIR /motolocation

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
