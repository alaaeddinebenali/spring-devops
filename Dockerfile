# Use the Java 8 base image with Alpine Linux
FROM openjdk:8-jre-alpine

# Install Maven
RUN apk update && \
    apk add --no-cache maven

# Copy the application code to the container
COPY . /app

# Set the working directory to the application directory
WORKDIR /app

# Build the application using Maven and move the jar file to the current directory
RUN mvn clean package -DskipTests && \
    mv target/tpAchatProject-1.0.jar ./

# Clean up unnecessary files and remove Maven
RUN mvn clean && \
    apk del maven && \
    rm -rf /var/cache/apk/* && \
    rm -rf target

# Expose port 8089 for the application
EXPOSE 8089

# Set the command to run when the container starts
CMD ["java", "-jar", "tpAchatProject-1.0.jar"]
