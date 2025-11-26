FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean install

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/LoginApp-0.0.1-SNAPSHOT.jar app.jar
# Copy the data directory to the container
COPY src/main/resources/data/ ./data/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]