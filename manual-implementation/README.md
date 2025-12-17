# Planetary System API

A Spring Boot RESTful API with GraphQL for managing planets and moons, featuring security, AOP logging, and centralised exception handling.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Authentication](#authentication)
- [REST Endpoints](#rest-endpoints)
- [GraphQL Endpoints](#graphql-endpoints)
- [Sample Requests/Responses](#sample-requestsresponses)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Known Issues/Missing Features](#known-issuesmissing-features)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- IDE (IntelliJ IDEA recommended)

## Setup Instructions

1. **Clone or navigate to the project directory:**

   ```bash
   cd "Project/planetary system api"
   ```

2. **Build the project:**

   ```bash
   mvn clean install
   ```

3. **Run the application:**

   ```bash
   mvn spring-boot:run
   ```

   Or run `PlanetarySystemApiApplication.java` from your IDE.

4. **Access the application:**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui/index.html`
   - H2 Console: `http://localhost:8080/h2-console`
   - GraphQL Endpoint: `http://localhost:8080/graphql`

## Running the Application

The application uses an in-memory H2 database that is automatically populated with sample data on startup:

- 5 sample planets (Earth, Mars, Jupiter, Saturn, Venus)
- 9 sample moons
- 3 sample users (admin, staff1, student1)

**Default credentials:**

- Username: `admin`, `staff1`, or `student1`
- Password: `password123`

## API Documentation

### Swagger UI

Interactive API documentation is available at:

```
http://localhost:8080/swagger-ui/index.html
```

Click "Authorize" and enter credentials to test endpoints directly.

## Authentication

The API uses **Basic Authentication**. All endpoints require authentication except:

- Swagger UI pages
- H2 Console

### User Roles

- **ADMIN**: Full access to all endpoints, including GraphQL user management
- **STAFF**: Can create, update, delete planets and moons (read/write access)
- **STUDENT**: Read-only access to planets and moons

## REST Endpoints

### Planets

| Method | Endpoint                   | Description                | Roles                 |
| ------ | -------------------------- | -------------------------- | --------------------- |
| POST   | `/api/planets`             | Create a new planet        | ADMIN, STAFF          |
| GET    | `/api/planets`             | Get all planets            | ADMIN, STAFF, STUDENT |
| GET    | `/api/planets/{id}`        | Get planet by ID           | ADMIN, STAFF, STUDENT |
| PATCH  | `/api/planets/{id}`        | Update planet              | ADMIN, STAFF          |
| DELETE | `/api/planets/{id}`        | Delete planet              | ADMIN, STAFF          |
| GET    | `/api/planets/type/{type}` | Get planets by type        | ADMIN, STAFF, STUDENT |
| GET    | `/api/planets/{id}/fields` | Get specific planet fields | ADMIN, STAFF, STUDENT |

### Moons

| Method | Endpoint                               | Description              | Roles                 |
| ------ | -------------------------------------- | ------------------------ | --------------------- |
| POST   | `/api/moons`                           | Create a new moon        | ADMIN, STAFF          |
| GET    | `/api/moons`                           | Get all moons            | ADMIN, STAFF, STUDENT |
| GET    | `/api/moons/{id}`                      | Get moon by ID           | ADMIN, STAFF, STUDENT |
| DELETE | `/api/moons/{id}`                      | Delete moon              | ADMIN, STAFF          |
| GET    | `/api/moons/planet/{planetName}`       | Get moons by planet name | ADMIN, STAFF, STUDENT |
| GET    | `/api/moons/planet/{planetName}/count` | Count moons by planet    | ADMIN, STAFF, STUDENT |

## GraphQL Endpoints

### Query

**Find User by ID:**

```graphql
query {
  findUserById(id: 1) {
    userId
    username
    role
    enabled
    createdAt
    updatedAt
  }
}
```

### Mutation

**Create User:**

```graphql
mutation {
  createUser(
    input: {
      username: "newuser"
      password: "password123"
      role: "STAFF"
      enabled: true
    }
  ) {
    userId
    username
    role
    enabled
  }
}
```

**Note:** GraphQL endpoints require ADMIN role.

## Sample Requests/Responses

### Create Planet (POST /api/planets)

**Request:**

```json
{
  "name": "Neptune",
  "type": "gas giant",
  "radiusKm": 24622.0,
  "massKg": 1.024e26,
  "orbitalPeriodDays": 60190.0
}
```

**Response (201 Created):**

```json
{
  "planetId": 6,
  "name": "Neptune",
  "type": "gas giant",
  "radiusKm": 24622.0,
  "massKg": 1.024e26,
  "orbitalPeriodDays": 60190.0,
  "moons": null
}
```

### Get Planet by ID (GET /api/planets/1)

**Response (200 OK):**

```json
{
  "planetId": 1,
  "name": "Earth",
  "type": "terrestrial",
  "radiusKm": 6371.0,
  "massKg": 5.972e24,
  "orbitalPeriodDays": 365.25,
  "moons": [
    {
      "moonId": 1,
      "name": "Moon",
      "diameterKm": 3474.8,
      "orbitalPeriodDays": 27.32,
      "planetName": "Earth"
    }
  ]
}
```

### Error Response (404 Not Found)

```json
{
  "timestamp": "2025-12-10T02:11:16.713",
  "status": 404,
  "error": "Not Found",
  "message": "Planet with ID 999 not found"
}
```

### Validation Error (400 Bad Request)

```json
{
  "timestamp": "2025-12-10T02:11:16.713",
  "status": 400,
  "error": "Validation Failed",
  "message": "Invalid input data",
  "validationErrors": [
    {
      "field": "name",
      "message": "Planet name is required"
    },
    {
      "field": "radiusKm",
      "message": "Radius must be positive"
    }
  ]
}
```

## Testing

### Testing REST Endpoints

1. **Via Swagger UI:**

   - Navigate to `http://localhost:8080/swagger-ui/index.html`
   - Click "Authorize" → Enter credentials (`admin` / `password123`)
   - Test endpoints directly from the UI

2. **Via Postman:**
   - Set Authorization: Basic Auth (`admin` / `password123`)
   - Set Content-Type: `application/json`
   - Send requests to `http://localhost:8080/api/planets` (or other endpoints)

### Testing GraphQL

1. **Via Postman:**
   - Method: POST
   - URL: `http://localhost:8080/graphql`
   - Authorization: Basic Auth (`admin` / `password123`)
   - Body (raw JSON):
     ```json
     {
       "query": "query { findUserById(id: 1) { userId username role } }"
     }
     ```

### Testing Security

- **As ADMIN:** All operations should work
- **As STAFF:** Can read/write planets and moons, cannot access GraphQL
- **As STUDENT:** Can only read, write operations return 403 Forbidden

### Testing Exception Handling

- **404 Not Found:** Request non-existent ID
- **409 Conflict:** Try creating duplicate planet name
- **400 Bad Request:** Send invalid/missing data
- **403 Forbidden:** Try write operation as STUDENT

### Testing AOP Logging

AOP logging is automatically active. Check console logs for:

-  Controller method logs
-  Transactional service method logs
-  Repository method logs

## Project Structure

```
src/main/java/ie/spring/planetary/
├── PlanetarySystemApiApplication.java
├── aspects/
│   └── LoggingAspect.java          # AOP logging with 3 pointcuts
├── config/
│   ├── SecurityConfig.java         # Spring Security configuration
│   ├── CustomUserDetailsService.java
│   └── OpenApiConfig.java          # Swagger/OpenAPI configuration
├── controllers/
│   ├── PlanetController.java       # REST endpoints for planets
│   ├── MoonController.java         # REST endpoints for moons
│   └── UserGraphQLController.java  # GraphQL resolvers for users
├── dtos/
│   ├── error/                      # Error response DTOs
│   ├── moon/                       # Moon DTOs
│   ├── planet/                     # Planet DTOs
│   └── user/                       # User DTOs
├── entities/
│   ├── Planet.java
│   ├── Moon.java
│   └── User.java
├── enums/
│   └── Role.java
├── exceptions/
│   ├── NotFoundException.java
│   ├── DuplicateEntityException.java
│   └── GlobalExceptionHandler.java # Centralised exception handling
├── mappers/
│   ├── PlanetMapper.java
│   ├── MoonMapper.java
│   └── UserMapper.java
├── repositories/
│   ├── PlanetRepository.java
│   ├── MoonRepository.java
│   └── UserRepository.java
└── services/
    ├── PlanetService.java / PlanetServiceImpl.java
    ├── MoonService.java / MoonServiceImpl.java
    └── UserService.java / UserServiceImpl.java
```

## Known Issues/Missing Features

### Current Status

 **Fully Implemented:**

- All REST endpoints for Planets and Moons
- GraphQL Query and Mutation for Users
- Basic Authentication with role-based access control
- Centralised exception handling
- AOP logging (3 pointcuts)
- Input validation
- Swagger/OpenAPI documentation

### Known Limitations

- **GraphQL:** Only User operations are implemented (not full GraphQL service for all entities)
- **No Unit/Integration Tests:** Tests are not required for this project
- **In-Memory Database:** Data is lost on application restart (H2 in-memory)
- **Deprecation Warnings:** `DaoAuthenticationProvider` constructor and methods show deprecation warnings (acceptable for manual implementation/learning)

## Technologies Used

- **Spring Boot 3.5.7**
- **Spring Data JPA** (Hibernate)
- **Spring Security** (Basic Authentication)
- **Spring GraphQL**
- **H2 Database** (in-memory)
- **Lombok**
- **Swagger/OpenAPI** (springdoc-openapi)
- **Spring AOP** (AspectJ)
- **Jakarta Validation**

