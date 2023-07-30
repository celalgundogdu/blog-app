FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

FROM openjdk:17-jdk-slim
WORKDIR social-app
COPY --from=build target/*.jar blog-app.jar
ENTRYPOINT ["java", "-jar", "blog-app.jar"]