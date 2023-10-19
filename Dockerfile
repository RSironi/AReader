FROM openjdk:17-jdk-alpine3.13

EXPOSE 8080

# Set environment variable
ENV GOOGLE_APPLICATION_CREDENTIALS=/app/areader-399020-ec5a3dd20423.json

# Add file to Docker image
ADD src/main/resources/areader-399020-ec5a3dd20423.json /app/

# Add application jar to Docker image
ADD target/areader-spring-docker.jar areader-spring-docker.jar

ENTRYPOINT [ "java", "-jar","areader-spring-docker.jar" ]