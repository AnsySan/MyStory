FROM openjdk:21-jdk-slim-buster
WORKDIR /app

COPY /userservice/build/libs/service.jar build/

WORKDIR /app/build
EXPOSE 8090
ENTRYPOINT java -jar service.jar