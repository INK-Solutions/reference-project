FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
ENV DB_HOST: $IP:$PORT
ENV DB_NAME: $DB_NAME
ENV DB_USER: $DB_USER
ENV DB_PASSWORD: $DB_PASSWORD

WORKDIR /app
COPY ./build/libs/lostemplateproject-0.0.1-SNAPSHOT.jar /app/lostemplateproject.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/lostemplateproject.jar"]
