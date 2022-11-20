FROM openjdk:17-alpine

ADD build/libs/coparts-0.0.1-SNAPSHOT.jar coparts.jar

ENTRYPOINT ["java", "-jar", "coparts.jar"]

EXPOSE 8080