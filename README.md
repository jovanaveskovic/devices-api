# Devices API

A REST API for managing devices built with Spring Boot and PostgreSQL database.

## Features
- Create a new device.
- Fully and/or partially update an existing device.
- Fetch a single device.
- Fetch all devices.
- Fetch devices by brand.
- Fetch devices by state.
- Delete a single device.

## Technical stack

### Core technologies
- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Maven 3.9
- Docker
- Swagger/ Springdoc OpenAPI

### Testing
- JUnit 5
- Mockito

## Requirements
To build this project, you will need to have at least the following:

- Java 21+
- Maven 3.9+

## Building the project and running tests
You can build the package by running the following command:
```
mvn clean install
```
To run only the tests, you should run the following command:
```
mvn test
```

## Starting service locally
To start the API you should run the following command:
```
docker compose up --build
```
It will start the following:
- **PostgreSQL** - listens on port 5432
- **devices-api** - listens on port 8080

### Access points
- API Base URL: http://localhost:8080/api/v1/devices
- Swagger UI: http://localhost:8080/swagger-ui.html
- API Docs: http://localhost:8080/api-docs
- Database: localhost:5432

To shut down the services gracefully run `ctrl+c`. To reset the data of the environment run `docker-compose down -v`.

## Future improvements
- Validation for changing the state of the devices
- Validation for unique name
- Implement pagination and sorting for device listings
- Add more filters to search (e.g. by name, creation time range, partial matches)
- Add integration tests
- Improve API documentation with detailed examples
- Add database migration with Flyway or Liquibase