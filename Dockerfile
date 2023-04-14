FROM maven:3.8.3-jdk-8
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
RUN ls .
RUN ls /app
RUN ls /app/target
RUN ls /app/target/
COPY /app/target/tpAchatProject-1.0-SNAPSHOT.war /app/tpAchatProject.war
CMD ["java", "-jar", "/app/tpAchatProject-1.0.war"]