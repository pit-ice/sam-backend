FROM mcr.microsoft.com/java/jdk:11-zulu-alpine 


ADD ./target/*.jar app.jar

ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]