FROM amazoncorretto:17-alpine-jdk

COPY ./bogo /usr/src/app/

ENTRYPOINT ["/usr/src/app/gradlew", "bootJar"]

# ENTRYPOINT ["java", "-jar", "/usr/src/app/app.jar"]