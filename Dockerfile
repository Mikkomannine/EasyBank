# Use an official Java runtime as a parent image
FROM maven:latest
# Set metadata information
LABEL authors="mikktma"

WORKDIR /usr/src
# Set the working directory in the container asdasd

# Copy the already built JAR from Jenkins workspace to the Docker image
COPY target/EasyBank-0.0.1-SNAPSHOT.jar /app/EasyBank-0.0.1-SNAPSHOT.jar

# Run the application
CMD ["java", "-jar", "/app/EasyBank-0.0.1-SNAPSHOT.jar"]




