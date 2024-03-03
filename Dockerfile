FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} scrapeservice.jar
ENTRYPOINT ["java","-jar","/scrapeservice.jar"]
EXPOSE 8090