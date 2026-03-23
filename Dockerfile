ARG MAVEN_BUILD_IMAGE=maven:3.9.11-eclipse-temurin-21
ARG JAVA_RUNTIME_IMAGE=eclipse-temurin:21-jre

FROM ${MAVEN_BUILD_IMAGE} AS build

WORKDIR /app

COPY pom.xml ./
RUN mvn --batch-mode --no-transfer-progress dependency:go-offline

COPY lombok.config ./
COPY src ./src

RUN mvn --batch-mode --no-transfer-progress -DskipTests package
RUN cp "$(find /app/target -maxdepth 1 -type f -name '*.jar' ! -name '*.original' | head -n1)" /app/app.jar

FROM ${JAVA_RUNTIME_IMAGE}

WORKDIR /app

COPY --from=build /app/app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
