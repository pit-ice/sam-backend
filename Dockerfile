# build stage
FROM maven:3.6.2-jdk-11 as build-stage
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package -DskipTests

# production stage 
FROM mcr.microsoft.com/java/jdk:11-zulu-alpine as production-stage

COPY --from=build-stage /usr/src/app/target/*.jar /usr/app/app.jar 

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/app/app.jar"]