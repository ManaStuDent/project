FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine-slim
VOLUME /tmp
WORKDIR /home
RUN mkdir lib
ARG jar_file
ADD ${jar_file} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dloader.path=lib","-Dspring.profiles.active=dev","-jar","app.jar"]