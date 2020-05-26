FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar ./

EXPOSE 9001

ENTRYPOINT ["java","-jar", "/app/api-gateway-0.0.1-SNAPSHOT.jar"]