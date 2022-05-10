# working
FROM openjdk:17
MAINTAINER Tomek-Jot
COPY target/SportDiary-0.0.1-SNAPSHOT.jar SportDiary-0.0.1.jar
COPY src/main/resources/db.json src/main/resources/db.json
ENTRYPOINT ["java","-jar","/SportDiary-0.0.1.jar"]