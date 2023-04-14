# Use the official Maven image as the base image
FROM maven:3.8.3-jdk-8

# Copy the application code to the container
COPY . /app

# Set the working directory to the application directory
WORKDIR /app

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use a Java runtime as the base image
FROM openjdk:8-jre-alpine

# Set the working directory in the container
WORKDIR /app

COPY target/tpAchatProject-1.0-SNAPSHOT.jar /app/tpAchatProject.jar

CMD ["java", "-jar", "/app/tpAchatProject.jar"]