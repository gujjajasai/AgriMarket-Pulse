# Use a lightweight OpenJDK 17 or 21 image as the base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged JAR file into the container
# CONFIRMED JAR NAME: price-analysis-service-0.0.1-SNAPSHOT.jar
COPY target/price-analysis-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application runs on (8082)
EXPOSE 8082

# Command to run the application when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]