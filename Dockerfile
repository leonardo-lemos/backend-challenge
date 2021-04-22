FROM maven:3-openjdk-11-slim as build

COPY . ./

RUN mvn package

FROM adoptopenjdk/openjdk11-openj9:alpine-jre as runtime
COPY --from=build target/backendchallenge-1.0.jar .

EXPOSE 8080

CMD ["java", "-jar", "backendchallenge-1.0.jar"]