FROM openjdk:11-jdk-slim

VOLUME /tmp
ADD target/company-0.0.1-SNAPSHOT.jar target/app.jar
RUN bash -c 'touch target/app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container","-jar","target/app.jar"]