FROM openjdk:latest
COPY ./target/group26-0.1.0.1-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group26-0.1.0.1-jar-with-dependencies.jar"]