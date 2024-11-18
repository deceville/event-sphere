# Backend (Java Spring Boot)

## Features
- JWT Authentication
- RESTful API endpoints for CRUD operations
- MySQL/PostgreSQL database integration

## Prerequisites
- Java 17+
- Maven 3+
- MySQL/PostgreSQL database

## How to Run
1. Configure `application.properties`.
2. Run: `mvn spring-boot:run`.

## Endpoints
| Method | Endpoint            | Description           |
|--------|---------------------|-----------------------|
| POST   | `/auth/login`       | Login endpoint        |
| GET    | `/events`           | List all events       |
| POST   | `/reservations`     | Create reservation    |
