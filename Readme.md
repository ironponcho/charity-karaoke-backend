# Charity-karaoke backend

This project is a backend for charity karaoke to organize and evaluate the performance of participants

Spring Boot project written in Kotlin


### Database

docker-compose.yml file will bring up the required postgresql server for developing purpose

Liquibase run migrations on application startup
 
### Docker

To build this application in Docker run:

`./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=charity-karaoke-backend` 

### Api

#### Json

Requests and Response should be convenient to [google's json styleguide](https://google.github.io/styleguide/jsoncstyleguide.xml).
