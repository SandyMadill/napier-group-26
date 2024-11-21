FROM openjdk:latest
COPY ./target/semga.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "semga.jar", "db:3306", "10000"]