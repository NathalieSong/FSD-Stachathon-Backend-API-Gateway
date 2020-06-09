FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY target/emart-api-gateway-0.0.1-SNAPSHOT.jar ./

EXPOSE 9010

ENTRYPOINT ["java","-jar", "/app/emart-api-gateway-0.0.1-SNAPSHOT.jar"]