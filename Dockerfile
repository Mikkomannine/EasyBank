# Use an official Maven image with OpenJDK 17
FROM maven:3.9.9-openjdk-17

# Set metadata information
LABEL authors="mikktma"

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml /app/

# Copy the entire project to the container
COPY . /app/

# Package your application with detailed logs
RUN mvn -X package

# Run the application
CMD ["java", "-jar", "target/easybank-0.0.1-SNAPSHOT.jar"]




