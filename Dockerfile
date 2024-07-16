FROM amazoncorretto:17.0.7-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} task1.jar
ENTRYPOINT ["java","-jar","/task1.jar"]
