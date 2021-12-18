FROM maven:3.8.2-jdk-11 as build
WORKDIR /workspace/app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

FROM adoptopenjdk:11-jre-openj9
COPY --from=build /workspace/app/target/crm-bh-1.0.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]
EXPOSE 8080