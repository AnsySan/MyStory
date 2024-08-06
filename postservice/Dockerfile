FROM openjdk:21-jdk-slim-buster
WORKDIR /app

COPY ./build/libs/postservice.jar build/

WORKDIR /app/build
EXPOSE 8080
ENTRYPOINT java -jar postservice.jar
