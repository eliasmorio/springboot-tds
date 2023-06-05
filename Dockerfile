# SpringBoot App

# Build war file with
FROM maven:3.9-eclipse-temurin-17-alpine AS build

COPY src /home/app/src

COPY pom.xml /home/app

RUN mvn -f /home/app/pom.xml clean package

# Run war file
FROM eclipse-temurin:17-jre-alpine

ENV JAVA_OPTS="-Xms256m -Xmx512m"

COPY --from=build /home/app/target/*.war /usr/local/lib/app.war

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/app.war"]

# Build image
# docker build -t spring-boot-app .

# Run image
# docker run -p 8080:8080 spring-boot-app