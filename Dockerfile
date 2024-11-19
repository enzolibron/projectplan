FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/projectplan-0.0.1-SNAPSHOT.jar /app/projectplan.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/projectplan.jar", "-webAllowOthers"]