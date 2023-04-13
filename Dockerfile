FROM maven:3.8.3-jdk-8
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/tpAchatProject-1.0.war .
CMD ["java", "-jar", "tpAchatProject-1.0.war"]