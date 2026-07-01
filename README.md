# Winter Olympics Management System

A Spring Boot application for managing Winter Olympic competitions (Slalom and Biathlon).

## Features
- **Competition Management**: CRUD for athletes and competitions.
- **Ranking Logic**:
  - **Slalom**: Handles Run 1 and Run 2 times with a top 30 qualification rule.
  - **Biathlon**: Calculates total time with a 60-second penalty for each miss.
- **Statistics**: Medal table by country, average athlete age, and youngest/oldest medalist tracking.
- **Security**: Role-based access control (ADMIN, ATHLETE, PUBLIC).
- **API Documentation**: Integrated Swagger UI.

## Getting Started
1. Configure your MySQL database in `src/main/resources/application.properties`.
2. Run the application: `./gradlew bootRun`
3. Access API Docs: `http://localhost:8080/swagger-ui.html`

## Default Credentials
- **Admin**: `admin` / `admin123`
