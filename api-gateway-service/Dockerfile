FROM eclipse-temurin:21-jre-alpine
WORKDIR ./build
ADD target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]