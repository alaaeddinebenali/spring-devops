FROM openjdk:17-alpine
EXPOSE 8083
ADD target/tpAchatProject-1.0.war tpAchatProject-1.0.war
ENTRYPOINT ["java","-jar","/tpAchatProject-1.0.war"]