# Planetary System API

Spring Boot 3.4.x application that exposes REST endpoints for planets and moons, GraphQL for user management, basic authentication/authorization, and observability via AOP and structured errors. Uses an in-memory H2 database seeded with sample data.

## What’s in the project
- **REST**: CRUD for planets and moons (`/api/planets`, `/api/moons`).
- **GraphQL**: User queries/mutations at `/graphql`; GraphiQL UI at `/graphiql`.
- **Security**: HTTP Basic + method-level roles (ADMIN, STAFF, STUDENT); BCrypt passwords.
- **Docs**: Swagger/OpenAPI UI at `/swagger-ui.html`.
- **Data**: H2 in-memory DB with seed data (planets, moons, users).
- **AOP**: Logging across service/controller/repository layers.
- **Error handling**: Global exception handler with structured responses.

## Key changes/highlights made
- Packages consolidated under `com.planetary` (removed old `com.example.planetary` structure).
- Added entities: `Planet`, `Moon`, `User` (with roles).
- Added DTOs, repositories, services, and controllers for planets/moons; GraphQL controller + service for users.
- Security tightened/whitelisted for tooling (H2, Swagger, GraphQL/GraphiQL, static assets); CSRF ignored for tooling endpoints; frame-options adjusted for H2.
- Seed data loader for users/planets/moons.
- OpenAPI config for basic auth docs.
- GraphiQL assets pinned to a working CDN version to try fix missing JS/CSS (see configuration).
- H2 URL kept alive for console use.

## Tech stack
- Java 17, Spring Boot 3.4.1
- Spring Web, Data JPA, Security, Validation, GraphQL, AOP
- H2 Database
- SpringDoc OpenAPI (Swagger)
- Lombok, AspectJ

## Run it
```bash
mvn spring-boot:run or straight from IntelliJ UI
```
App starts on `http://localhost:8080`.

## Credentials (seeded)
| user    | pass       | role    | access                               |
|---------|------------|---------|--------------------------------------|
| admin   | admin123   | ADMIN   | full                                 |
| staff   | staff123   | STAFF   | manage planets/moons                 |
| student | student123 | STUDENT | read-only planets/moons              |

## Endpoints
- REST (Basic Auth):
  - `POST /api/planets` (ADMIN/STAFF)
  - `GET /api/planets` (ALL)
  - `GET /api/planets/{id}` (ALL)
  - `GET /api/planets/type/{type}` (ALL)
  - `PUT /api/planets/{id}` (ADMIN/STAFF)
  - `DELETE /api/planets/{id}` (ADMIN)
  - `POST /api/moons` (ADMIN/STAFF)
  - `GET /api/moons` (ALL)
  - `GET /api/moons/{id}` (ALL)
  - `GET /api/moons/planet/{planetId}` (ALL)
  - `PUT /api/moons/{id}` (ADMIN/STAFF)
  - `DELETE /api/moons/{id}` (ADMIN)

- GraphQL:
  - Endpoint: `/graphql`
  - UI: `/graphiql?path=/graphql`

  # EXAMPLE OF WHAT SHOULD HAVE HAPPENED WHEN LOADED
  - Example query:
    ```graphql
    query {
      findUserById(id: 1) {
        id
        username
        role
      }
    }
    ```
  - Example mutation:
    ```graphql
    mutation {
      createUser(username: "newuser", password: "pass123", role: "STUDENT") {
        id
        username
        role
      }
    }
    ```

## Tooling & consoles
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- GraphiQL: `http://localhost:8080/graphiql?path=/graphql`
- H2 Console: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:planetary_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
  - User: `sa`, Password: *(blank)*

## Configuration notes (application.properties)
- H2 keep-alive: `jdbc:h2:mem:planetary_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
  - Initially he page was loaded with the wrong JDBC URL so that was causing some confusion when trying to connect to it,
    but all it took was to either chnage the spring datasource url or the name in the h2 console
- GraphQL/GraphiQL: `/graphql`, `/graphiql`, CDN enabled with `spring.graphql.graphiql.cdn.version=2.3.0`
  - Updated the SecurityConfig.java to allow unauthenticated access to GraphiQL but still didnt work
  - GraphiQL was blank because its JS/CSS assets are 404. Spring’s GraphiQL UI fetches files from a CDN (unpkg) at whatever version it’s configured to use.
    The default version Spring picked (5.2.2) isn’t available on unpkg, so the browser couldn't load graphiql.min.js / graphiql.min.css, and the UI never renders.

    I tried to pin the CDN version to a known good one (2.3.0) and kept CDN enabled in application.properties:

    - spring.graphql.graphiql.cdn.enabled=true

    - spring.graphql.graphiql.cdn.version=2.3.0
    
  But this still didnt work and at this point claude was hallucinating 

- Swagger paths: `/swagger-ui.html`, `/api-docs`
- Security permits tooling endpoints and static assets; CSRF ignored for tooling.

## Manual fixes & notes

- **Endpoint access & authentication**
  - Initial attempts to hit `/api/**` and tooling endpoints failed until the correct Basic Auth users were consistently used (`admin/admin123`, `staff/staff123`, `student/student123`).
  - Swagger UI already had the security scheme wired; authorisation had to be set once in Swagger to test each role.

- **DataLoader vs `data.sql`**
  - Having both `DataLoader` and `data.sql` caused clashes in startup order and duplicate data; the project now relies on `DataLoader` only (the `data.sql` file was removed).
  - `spring.jpa.hibernate.ddl-auto=create-drop` and `spring.jpa.defer-datasource-initialization=true` are kept so entities drive schema creation and the data loader runs cleanly each boot.

- **Verbose SQL/logging**
  - `spring.jpa.show-sql=true` and Hibernate SQL/binder logging at DEBUG/TRACE are intentionally left on, even though they make the console noisy, because that’s how the reference code was provided and it helps when inspecting generated SQL.

- **Version compatibility**
  - Upgraded to Spring Boot `3.4.1` and aligned starters (web, data-jpa, security, validation, graphql, aop) to avoid older 2.x/early-3.x incompatibilities.
  - Added `maven.compiler.source/target=17` to ensure Java 17 compatibility.

- **Role testing via Swagger**
  - Using Swagger UI, each role was exercised against the REST endpoints:
    - **Student**: can `GET` planets/moons but receives 403 on `POST /api/planets` and other write operations, as expected.
    - **Staff**: can create/update planets and moons but must not be able to delete them.
    - **Admin**: full CRUD.
  - A bug was found where **staff** could delete a moon; this was fixed in `SecurityConfig` by restricting `DELETE /api/moons/**` to `hasRole('ADMIN')` only.

- **GraphiQL issues**
  - Initially, GraphQL endpoints were protected by Basic Auth, and GraphiQL could not prompt for credentials, leaving the UI stuck on “Loading…”. Security was adjusted to allow unauthenticated access to `/graphql` and `/graphiql` while still enforcing roles at the method level.
  - Even after auth changes, GraphiQL stayed blank because its JS/CSS assets were returning 404 from the default CDN (unpkg) at version `5.2.2`. Spring Boot’s auto-configured GraphiQL expected that version, which doesn’t exist upstream.
  - A manual attempt was made to pin the CDN to a known version (`spring.graphql.graphiql.cdn.enabled=true`, `spring.graphql.graphiql.cdn.version=2.3.0`). This reduced 404s but the UI still did not behave reliably, so GraphQL is best tested via tools like HTTP clients (Insomnia/Postman/curl) using the `/graphql` endpoint.

- **H2 console confusion**
  - The H2 console initially prefills a different in-memory DB name, which led to “database not found” errors.
  - Fix is purely configuration/usage: ensure the JDBC URL in the console matches the app’s configuration exactly (`jdbc:h2:mem:planetary_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`)

- **REST API behaviour**
  - `/api/planets` and `/api/moons` are fully protected by Spring Security; unauthenticated calls return 401, and role constraints (ADMIN/STAFF/STUDENT) are enforced both in the security config and at method level with `@PreAuthorize`.
  - Tested via Swagger UI and direct HTTP clients to verify that the behaviour matches the role matrix described above.

