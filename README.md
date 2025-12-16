# Planetary System API - AI Code Generation Comparison

## Project Overview

This repository contains a comparative analysis of **manually-written code** versus **AI-generated code** for a Spring Boot REST API project. The project implements a Planetary System API with entities for Planets, Moons, and Users.

**Course:** Application Development  
Students : Victor Orimolusi, Edward Inegbenosun

---

## Repository Structure

```
planetary-system-api-comparison/
├── manual-implementation/          # Hand-coded Spring Boot project
│   ├── src/
│   ├── pom.xml
│   └── README.md                   # Documentation of manual implementation
│
├── ai-generated-gpt/              # Code generated using GPT-5
│   ├── src/
│   ├── pom.xml
│   └── README.md                   # AI generation notes and missing features
│
├── ai-generated-claude/            # Code generated using Claude Sonnet 4.5
│   ├── src/
│   ├── pom.xml
│   └── README.md                   # AI generation notes and missing features
│
├── docs/                           # Documentation and analysis
│   ├── AI-Generation-Prompts.md    # All prompts used with AI tools
│   ├── Technical-Report.pdf        # Comparative analysis
│   └── test-results/
│       ├── gpt5-test-results.txt
│       └── claude-test-results.txt
│
├── .gitignore
└── README.md                       # This file
```

---

## Project Requirements

### Technical Stack
- **Java**: 17+
- **Spring Boot**: 3.5.x
- **Build Tool**: Maven
- **Database**: H2 (in-memory)
- **Security**: Basic Authentication with BCrypt
- **API Documentation**: Swagger/OpenAPI
- **GraphQL**: User entity operations
- **Logging**: AspectJ (AOP)

### Key Features
1. **REST API** for Planets and Moons (CRUD operations)
2. **GraphQL** for User operations (findUserById, createUser)
3. **Role-Based Access Control** (ADMIN, STAFF, STUDENT)
4. **DTOs** for all API requests/responses
5. **Centralized Exception Handling** (@ControllerAdvice)
6. **AspectJ Logging** with 3+ pointcuts
7. **Custom JPA Queries** (@Query annotations)
8. **Sample Data Preloading**

---

## AI Tools Used

1. **GPT-5** (OpenAI)
2. **Claude Sonnet 4.5** (Anthropic)

Both tools received the **same initial prompt** to ensure fair comparison.

---

## Comparative Analysis Focus

The technical report evaluates:
- ✅ **Functionality Coverage**: Which features were implemented correctly?
- ✅ **Code Quality**: Readability, maintainability, structure
- ✅ **Spring Boot Best Practices**: DTOs, layering, validation, annotations
- ✅ **Security Implementation**: Authentication, authorization, password hashing
- ✅ **Exception Handling & Logging**: @ControllerAdvice, AspectJ
- ✅ **Code Efficiency**: Complexity and design patterns

---

## Getting Started

### Running the Manual Implementation
```bash
cd manual-implementation
mvn clean install
mvn spring-boot:run
```

### Running AI-Generated Code
```bash
cd ai-generated-gpt5  # or ai-generated-claude
mvn clean install
mvn spring-boot:run
```

### Accessing the Application
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console
- **GraphQL**: http://localhost:8080/graphql

### Test Credentials
| Username | Password | Role |
|----------|----------|------|
| admin    | admin123 | ADMIN |
| staff    | staff123 | STAFF |
| student  | student123 | STUDENT |

---

## Assessment Breakdown

- **Manual Implementation**: 40%
- **Security & Role Enforcement**: 10%
- **AI Code Comparison**: 40%
- **Technical Report Quality**: 10%

---

## Documentation

All prompts, test results, and comparative analysis are documented in the `/docs` folder.

---

## Links

- [Project Specification](docs/specification.pdf)
- [Technical Report](docs/Technical-Report.pdf)
- [AI Prompts](docs/AI-Generation-Prompts.md)

---

## Project Timeline

This repository demonstrates the development timeline through commit history:
1. Initial setup and manual implementation
2. AI code generation (GPT-5)
3. AI code generation (Claude Sonnet 4.5)
4. Testing and evaluation
5. Technical report and documentation

---

*This project is part of the Application Development module assessment at MTU Cork.*
