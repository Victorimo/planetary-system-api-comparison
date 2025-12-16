# 🌍 Planetary System API

A production-grade Spring Boot application providing REST and GraphQL APIs for managing Planets, Moons, and Users in a planetary system.

This project demonstrates clean architecture, Spring Security, DTO-based APIs, JPA, GraphQL, and Aspect-Oriented Programming, built using Java 17 and Spring Boot 3.x.

## 📌 Features

- REST API for Planets and Moons
- GraphQL API for Users
- Role-based access control (ADMIN / STAFF / STUDENT)
- BCrypt password encryption
- DTO-only API (entities never exposed)
- Global exception handling
- Aspect-oriented logging (AOP)
- In-memory H2 database
- Swagger / OpenAPI documentation
- GraphiQL UI for GraphQL testing
- Preloaded sample data

## 🧱 Tech Stack

| Technology | Purpose |
|------------|---------|
| Java 17 | Language |
| Spring Boot 3.x | Application framework |
| Spring Web | REST APIs |
| Spring Data JPA | Persistence |
| Spring Security | Authentication & Authorization |
| Spring GraphQL | GraphQL API |
| Spring AOP | Logging |
| H2 Database | In-memory database |
| Lombok | Boilerplate reduction |
| SpringDoc OpenAPI | Swagger documentation |
| Maven | Build tool |

## 🏗️ Architecture

```
Controller
   ↓
Service
   ↓
Repository
   ↓
Database
```

- Strict separation of concerns
- DTOs used for all request/response payloads
- Entities are never exposed

## 🔐 Security Model

### Authentication

- HTTP Basic Authentication
- Users stored in database
- Passwords encrypted using BCrypt

### Authorization

| Role | Permissions |
|------|-------------|
| ADMIN | Full access (REST + GraphQL) |
| STAFF | Create / Update / Delete Planets & Moons |
| STUDENT | Read-only access to Planets & Moons |
| STUDENT | ❌ No access to Users |

## 👥 Default Users (Preloaded)

| Username | Password | Role |
|----------|----------|------|
| admin | admin123 | ADMIN |
| staff | staff123 | STAFF |
| student | student123 | STUDENT |

## 🌐 REST API Endpoints

### Planets

- `POST` `/planets`
- `GET` `/planets`
- `GET` `/planets/{id}`
- `GET` `/planets/type/{type}`
- `PUT` `/planets/{id}`
- `DELETE` `/planets/{id}`

### Moons

- `POST` `/moons`
- `GET` `/moons`
- `GET` `/moons/{id}`
- `GET` `/moons/planet/{planetId}`
- `PUT` `/moons/{id}`
- `DELETE` `/moons/{id}`

## 🔎 GraphQL API (Users only)

**Endpoint:** `/graphql`

**GraphiQL UI:** `/graphiql`

### Queries

```graphql
query {
  findUserById(id: 1) {
    id
    username
    role
  }
}
```

### Mutations

```graphql
mutation {
  createUser(username: "newuser", password: "pass123", role: "STUDENT") {
    id
    username
    role
  }
}
```

> ⚠️ **Note:** GraphQL user operations are ADMIN-only

## 📄 API Documentation (Swagger)

**Swagger UI:**
- http://localhost:8080/swagger-ui.html

**OpenAPI JSON:**
- http://localhost:8080/v3/api-docs

## 🗄️ H2 Database Console

**URL:** http://localhost:8080/h2-console

| Property | Value |
|----------|-------|
| JDBC URL | `jdbc:h2:mem:planetarydb` |
| Username | `sa` |
| Password | _(empty)_ |

## ▶️ Running the Application

### Prerequisites

- Java 17+
- Maven

### Run with Maven

```bash
mvn spring-boot:run
```

### Build & Run JAR

```bash
mvn clean package
java -jar target/planetary-system-1.0.0.jar
```

## 🧪 Sample Data

Preloaded at startup:

- **2 Planets:** Earth, Mars
- **Moons:** Moon, Phobos, Deimos
- **3 Users:** ADMIN / STAFF / STUDENT

## 🧠 Aspect-Oriented Programming

### Logging Aspect

- Logs before service method execution
- Logs after controller responses
- Logs around repository calls (execution time)

### Purpose

- Clean separation of cross-cutting concerns
- Production-style observability

## ❗ Error Handling

- Global `@ControllerAdvice`
- Structured JSON error responses

### Handles

- Resource not found
- Validation errors
- Bad requests
- Unexpected server errors

## 📁 Project Structure

```
com.example.planetarysystem
├── aop
├── config
├── controller
├── domain
├── dto
├── exception
├── graphql
├── mapper
├── repository
├── security
├── service
└── PlanetarySystemApplication.java
```
## 🛠️ Manual Fixes & Notes

- Removed an accidental empty `data.sql`; startup seeding now relies on `DataLoader` so passwords are properly BCrypt-hashed and sample data is loaded once.
- Springdoc compatibility: Spring Boot 3.5.x requires Springdoc 2.8.x (previously 2.6.x caused Swagger to 500).
- Validation: replaced `@Min` on decimal DTO fields with `@Positive` to avoid invalid constraint usage on `Double`.
- GraphQL: moved `UserGraphQLController` from `src/main/resources/graphql` to `src/main/java/com/example/planetarysystem/graphql` so it is detected as a Spring bean.
- GraphQL ID handling: resolvers now take `@Argument String id` and convert to `Long` before service calls.
- GraphQL errors: added `GraphQLExceptionHandler` to map `ResourceNotFoundException` → `ErrorType.NOT_FOUND` and `BadRequestException` → `ErrorType.BAD_REQUEST`, preventing “non-nullable field returned null” failures.