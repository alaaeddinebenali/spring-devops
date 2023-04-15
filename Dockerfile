#FROM maven:3.8.3-jdk-8

#COPY . /app

#WORKDIR /app

#RUN mvn clean package -DskipTests

#FROM openjdk:8-jre-alpine

#WORKDIR /app

#COPY target/tpAchatProject-1.0-SNAPSHOT.war /app/tpAchatProject.war

#EXPOSE 8089

#CMD ["java", "-jar", "/app/tpAchatProject.war"]
FROM maven:3.8.3-jdk-8
COPY . /app
WORKDIR /app
RUN mvn clean package -DskipTests
FROM openjdk:8-jre-alpine
EXPOSE 8089
COPY target/tpAchatProject-1.0-SNAPSHOT.jar /app/tpAchatProject.jar
RUN chmod +x /app/tpAchatProject.jar
CMD ["java", "-jar", "/app/tpAchatProject.jar"]