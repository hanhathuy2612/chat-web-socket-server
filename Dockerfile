FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .

COPY gradlew gradlew
RUN chmod +x gradlew
RUN ./gradlew clean build

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]