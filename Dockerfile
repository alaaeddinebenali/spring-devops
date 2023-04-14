FROM maven:3.8.3-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/tpAchatProject-1.0.jar /app/tpAchatProject.jar
CMD ["java", "-jar", "/app/tpAchatProject.jar"]