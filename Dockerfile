FROM openjdk:17-alpine

ADD build/libs/Vedmid_Andrii-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080