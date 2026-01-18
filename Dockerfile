FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY target/toolbox-health-check-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]