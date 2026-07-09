# lab2a

This workspace contains two API implementations for the same training project:

- a Java Spring Boot service with customer, flight, and booking endpoints
- a Node.js/Express service for customer management with Swagger docs

## Overview

The Java API is the main implementation for the current lab. It uses Spring Boot, Spring Data JPA, validation, and an in-memory H2 database. The Node service is a lightweight alternative implementation for customer CRUD operations and includes Swagger documentation.

## Technologies

### Java API
- Java 21
- Spring Boot 4.0.7
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- H2 Database (in-memory)
- Springdoc OpenAPI / Swagger UI
- Maven

### Node API
- Node.js
- Express
- Swagger JSDoc + Swagger UI
- SQLite (in-memory)

## How to Build

### Build Java JAR (local)

From project root:

```bash
./mvnw clean package -DskipTests
```

On Windows:

```powershell
.\mvnw.cmd clean package -DskipTests
```

Generated artifact:

- target/lab2a-0.0.1-SNAPSHOT.jar

### Build Docker image

The Dockerfile uses eclipse-temurin:21-jdk-jammy as runtime base image.

```bash
docker build -t lab2a-java:latest .
```

### Run with Docker

```bash
docker run --rm -p 8080:8080 lab2a-java:latest
```

### Run with Docker Compose

docker-compose.yml runs the jar directly from target/lab2a-0.0.1-SNAPSHOT.jar.

```bash
docker compose up -d
```

To stop:

```bash
docker compose down
```

## Running the Java application

From the project root, run:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The Java API starts on http://localhost:8080 by default.

Swagger UI is available at:
- http://localhost:8080/swagger-ui/index.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Java API endpoints

Base path: /v1

### Customers
- GET /v1/customers
- GET /v1/customer/{custId}
- POST /v1/customers
- DELETE /v1/customers/{custId}

Example:

```bash
curl "http://localhost:8080/v1/customers?page=0&limit=10"
curl http://localhost:8080/v1/customer/1
```

### Flights
- GET /v1/flights?origin=JFK&destination=LAX&departureDate=2026-07-20
- GET /v1/flights/{flightId}

### Bookings
- POST /v1/bookings
- GET /v1/bookings/{bookingId}
- PATCH /v1/bookings/{bookingId}
- POST /v1/bookings/{id}/cancellation

## Running the Node application

From the Node folder:

```bash
cd node
npm install
npm start
```

The Node API also runs on http://localhost:8080 by default.

Swagger documentation is available at:
- http://localhost:8080/api-docs

### Node customer endpoints
- GET /v1/customers
- GET /v1/customer/:custId
- POST /v1/customers
- DELETE /v1/customers/:custId

## Database

### Java service
The Java API uses an in-memory H2 database configured in src/main/resources/application.properties.

- JDBC URL: jdbc:h2:mem:lab2a;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
- Schema is recreated on startup with create-drop

### Node service
The Node service uses an in-memory SQLite database initialized at startup.

## Testing

Run the Java test suite with:

```bash
./mvnw test
```

## Notes

- Customer payloads are validated in both implementations.
- The Java API includes seeded sample flight data for search and booking flows.
