FROM maven:3.8.1-openjdk-17-slim AS build
COPY /src /app/src
COPY /pom.xml /app
RUN mvn -f /app/pom.xml clean package -Dmaven.test.skip

FROM openjdk:17-jdk-alpine3.13
EXPOSE 8080

COPY --from=build /app/target/*.jar app.jar

#ENV GOOGLE_APPLICATION_CREDENTIALS=/app/areader-399020-ec5a3dd20423.json
#ADD src/main/resources/areader-399020-ec5a3dd20423.json /app/
#ADD target/areader-spring-docker.jar areader-spring-docker.jar

ENTRYPOINT [ "java", "-jar","app.jar" ]