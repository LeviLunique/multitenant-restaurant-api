ARG MAVEN_BUILD_IMAGE=maven:3.9.11-eclipse-temurin-21
ARG JAVA_RUNTIME_IMAGE=eclipse-temurin:21-jre
ARG APP_JAR_FILE=multitenant-restaurant-api-0.0.7-2-SNAPSHOT.jar

FROM ${MAVEN_BUILD_IMAGE} AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn --batch-mode --no-transfer-progress dependency:go-offline

COPY lombok.config ./
COPY src ./src

RUN mvn --batch-mode --no-transfer-progress -DskipTests package

FROM ${JAVA_RUNTIME_IMAGE}

ARG APP_JAR_FILE

WORKDIR /app

COPY --from=build /app/target/${APP_JAR_FILE} /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
