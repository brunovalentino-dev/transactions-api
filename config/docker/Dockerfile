FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR application

COPY ../../.mvn/ ./.mvn
COPY ../../mvnw pom.xml ./
COPY ../../src ./src

RUN --mount=type=cache,target=/root/.m2 ./mvnw install -DskipTests

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} target/application.jar

RUN java -Djarmode=layertools -jar target/application.jar extract

FROM eclipse-temurin:17-jre-alpine
WORKDIR application

RUN addgroup -S apigroup && adduser -S client -G apigroup
USER client

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]
