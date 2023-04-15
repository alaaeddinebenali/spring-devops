#FROM maven:3.8.3-jdk-8

#COPY . /app

#WORKDIR /app

#RUN mvn clean package -DskipTests

#FROM openjdk:8-jre-alpine

#WORKDIR /app

#COPY target/tpAchatProject-1.0-SNAPSHOT.war /app/tpAchatProject.war

#EXPOSE 8089

#CMD ["java", "-jar", "/app/tpAchatProject.war"]

FROM openjdk:8-jre-alpine
EXPOSE 8089
ADD target/tpAchatProject-1.0-SNAPSHOT.war /app/tpAchatProject.war
ENTRYPOINT ["java", "-jar", "/tpAchatProject.war"]