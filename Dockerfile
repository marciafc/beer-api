FROM adoptopenjdk/maven-openjdk11
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests
CMD ["java", "-jar", "./target/beer-api-0.0.1-SNAPSHOT.jar"]