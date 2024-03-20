FROM amazoncorretto:17-alpine-jdk

COPY ./build/libs/bogo-0.0.1-SNAPSHOT.jar /usr/src/app/app.jar

ENTRYPOINT ["java", "-jar", "/usr/src/app/app.jar"]