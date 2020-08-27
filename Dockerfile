FROM maven:3.6.2-jdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
 
FROM mcr.microsoft.com/java/jdk:11-zulu-alpine 

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar 
EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/app/app.jar"]