FROM openjdk:21-jdk-slim-buster
WORKDIR /app

COPY /build/libs/MicroservicesProject.jar build/

WORKDIR /app/build
EXPOSE 8091
ENTRYPOINT java -jar MicroservicesProject.jar