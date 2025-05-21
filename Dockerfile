FROM openjdk:17-jdk-slim-buster

WORKDIR /motolocation

COPY target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
