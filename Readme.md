# Charity-karaoke backend

This project is a backend for charity karaoke to organize and evaluate the performance of participants

Spring Boot project written in Kotlin

### Linting 

Usage of ktlint: 

Check for linting errors: `mvn antrun:run@ktlint`

To automatically fix linting errors run: `mvn antrun:run@ktlint-format`

### Database

docker-compose.yml file will bring up the required postgresql server for developing purpose

Liquibase runs migrations on application startup
 
### Docker

To build this application in Docker run:

`./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=charity-karaoke-backend` 

### Api

#### Documentation

#### Json

Requests and Response should be convenient to [google's json styleguide](https://google.github.io/styleguide/jsoncstyleguide.xml).
