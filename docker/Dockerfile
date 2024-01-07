FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/word-processor-service-0.0.1-SNAPSHOT.jar word-processor-service.jar
ENTRYPOINT ["java","-jar","/word-processor-service.jar"]
