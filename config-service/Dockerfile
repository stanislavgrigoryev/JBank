FROM eclipse-temurin:21-jre-alpine
WORKDIR ./build
ADD target/*.jar app.jar
EXPOSE 9297

ENTRYPOINT ["java", "-jar", "app.jar"]