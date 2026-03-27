# NexusCart Backend - README

## Overview
Production-level e-commerce backend built with Spring Boot 3, Java 21, and MySQL.

## Tech Stack
- **Java**: 21
- **Framework**: Spring Boot 3.2.0
- **Database**: MySQL 8.0+
- **Cache**: Redis
- **Security**: Spring Security 6 + JWT
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven

## Project Structure
```
src/main/java/com/nexuscart/
├── config/              # Application configuration classes
├── security/            # Security configuration and JWT handling
├── common/
│   ├── exception/       # Custom exceptions and global handler
│   └── model/           # Common DTOs and models
├── module_auth/         # Authentication module
├── module_product/      # Product catalog module
├── module_cart/         # Shopping cart module
└── module_order/        # Order management module
```

## Prerequisites
- Java 21+
- MySQL 8.0+
- Redis (optional for caching)
- Maven 3.9+

## Configuration
Update `application.properties` with your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nexuscart
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Or run the JAR
java -jar target/nexuscart-backend-1.0.0-SNAPSHOT.jar
```

## API Documentation
Once running, access Swagger UI at:
```
http://localhost:8080/api/v1/swagger-ui.html
```

OpenAPI spec available at:
```
http://localhost:8080/api/v1/v3/api-docs
```

## Default Port
The application runs on port `8080` with context path `/api/v1`

## Modules Status
- ✅ Project structure setup
- ✅ Maven dependencies configured
- ✅ Database schema (Flyway migrations)
- ✅ Security configuration (JWT ready)
- ✅ Exception handling
- ⏳ Business logic implementation pending

## Next Steps
1. Implement UserDetailsService for authentication
2. Add JWT token generation and validation
3. Implement entity classes and repositories
4. Add service layer business logic
5. Complete controller endpoints
