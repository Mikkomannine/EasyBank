# Use an official Java runtime as a parent image
FROM eclipse-temurin:21-jre-alpine

# Set metadata information
LABEL authors="mikktma"

# Set the working directory in the container asdasd
WORKDIR /app

# Copy the already built JAR from Jenkins workspace to the Docker image
COPY target/EasyBank-0.0.1-SNAPSHOT.jar /app/EasyBank-0.0.1-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/app/EasyBank-0.0.1-SNAPSHOT.jar"]





