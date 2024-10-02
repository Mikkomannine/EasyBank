
FROM maven:latest

# Set metadata information
LABEL authors="mikktma"

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file to the container
COPY pom.xml /app/

# Copy the entire project to the container
COPY . /app/

# Package your application
RUN mvn package

# Run the application
CMD ["java", "-jar", "target/Easybank-0.0.1-SNAPSHOT.jar"]




