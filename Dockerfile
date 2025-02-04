FROM maven:3-jdk-11 AS maven
WORKDIR /app/
COPY pom.xml .
COPY src ./src/
RUN mvn package

FROM openjdk:11-jre-slim
COPY --from=maven /app/target/asana-task-updates-1.0-SNAPSHOT-jar-with-dependencies.jar /app/
WORKDIR /app/
ENTRYPOINT ["java", "-jar", "/app/asana-task-updates-1.0-SNAPSHOT-jar-with-dependencies.jar"]