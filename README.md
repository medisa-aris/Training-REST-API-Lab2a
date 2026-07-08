# lab2a

A simple Spring Boot REST API for managing customers using Spring Data JPA and an in-memory H2 database.

## Overview

This application exposes REST endpoints for customer CRUD operations and supports both page-based and cursor-based pagination for retrieving customers.

## Technologies

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- H2 Database (in-memory)
- Maven

## Running the application

Use Maven to run the application from the project root:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts on `http://localhost:8080` by default.

## API Endpoints

Base path: `/v1`

### Get customers

- `GET /v1/customers`
- Supports optional pagination parameters:
  - `page` (zero-based page number)
  - `limit` (page size)
- Supports cursor-based pagination with:
  - `cursor` (customer ID offset)

Examples:

```bash
curl "http://localhost:8080/v1/customers?page=0&limit=20"
curl "http://localhost:8080/v1/customers?cursor=5&limit=20"
```

### Get a customer by ID

- `GET /v1/customer/{custId}`

Example:

```bash
curl http://localhost:8080/v1/customer/1
```

### Create a new customer

- `POST /v1/customers`
- Request body should contain customer JSON.
- Returns `201 Created`.

Example body:

```json
{
  "firstName": "Jane",
  "lastName": "Doe",
  "email": "jane.doe@example.com",
  "phone": "+1234567890",
  "address": "123 Main St",
  "city": "Springfield",
  "country": "USA"
}
```

### Delete a customer

- `DELETE /v1/customers/{custId}`
- Returns `204 No Content` if the customer is deleted.

Example:

```bash
curl -X DELETE http://localhost:8080/v1/customers/1
```

## Database

The application uses an in-memory H2 database configured in `src/main/resources/application.properties`.

- JDBC URL: `jdbc:h2:mem:lab2a;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- H2 console is enabled.

## Notes

- Validation is applied to customer fields.
- The application drops and recreates the schema on each startup (`spring.jpa.hibernate.ddl-auto=create-drop`).
