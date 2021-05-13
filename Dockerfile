FROM openjdk:11
EXPOSE 8080
ADD target/spring-waluta-docker.jar spring-waluta-docker.jar
ENTRYPOINT ["java", "-jar","/spring-waluta-docker.jar"]

