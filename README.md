 
# 🛒 Ecommerce Spring Boot Application

A RESTful ecommerce backend application built using **Spring Boot**, **Spring Security**, and **JWT authentication**.  
This project implements core ecommerce functionalities such as user authentication, product management, cart handling, and order processing using clean layered architecture.

---

## ✨ Features

- User registration and login with JWT authentication
- Role-based authorization (ADMIN / USER)
- Secure APIs using Spring Security
- Product management (Create, Update, Delete, View)
- Cart management (Add, update, remove items)
- Order placement and order tracking
- Global exception handling with custom error responses
- Stateless authentication using JWT tokens

---

## 🏗️ Project Architecture

The application follows a **layered architecture** for maintainability and scalability.

## 📂 Project Structure

src/main/java/com/nayan/ecommerce/
├── config/        # Security and application configuration
├── controller/    # REST controllers (API layer)
├── dto/           # Request and response DTOs
├── entity/        # JPA entities and enums
├── exception/     # Custom exceptions and handlers
├── repository/    # JPA repositories
├── security/      # JWT filters and utilities
├── service/       # Business logic layer
└── EcommerceProjectApplication.java
 
---

# Tech Stack

- **Java 24**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA / Hibernate**
- **Maven**
- **H2 / MySQL**
- **Lombok**

---

## Getting Started

### Prerequisites

- Java 24+
- Maven
- Git

---

### Clone the Repository

```bash
git clone https://github.com/nayan-9860/ecommerce-spring-boot.git
cd ecommerce-spring-boot

```

### Run the Application
```
mvn clean install
mvn spring-boot:run

```
### Authentication & Authorization

This project uses JWT-based authentication.

Auth Endpoints

Method    Endpoint	              Description
-------|------------------------|---------------------
POST	  |  /api/auth/register	   | Register new user
POST	  |  /api/auth/login	      | Login and get JWT token

After login, include the token in headers:
Authorization: Bearer <JWT_TOKEN>

 📦 API Endpoints Overview

Products
Method	     Endpoint	            Access
--------|----------------------|------------
GET	    |  /api/products	      |  Public
POST	   |  /api/products	      |  ADMIN
PUT	    |  /api/products/{id}	 |  ADMIN
DELETE	 |  /api/products/{id}	 |  ADMIN


Cart
Method	     Endpoint
---------|-----------------------
POST	    |   /api/cart/add
PUT	     |   /api/cart/update
DELETE	  |   /api/cart/remove
GET	     |   /api/cart

Orders
Method	     Endpoint
---------|----------------
POST	    |   /api/orders
GET	     |   /api/orders

🗄️ Database Configuration

Uses H2 in-memory database by default
Can be switched to MySQL

Example MySQL configuration:
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update


🧪 Exception Handling

Centralized exception handling using @ControllerAdvice
Custom exceptions:
BadRequestException
ResourceNotFoundException
ConflictException

Meaningful JSON error responses

🛡️ Security Highlights
Stateless session management
Custom JWT authentication filter
Custom 401 (Unauthorized) and 403 (Forbidden) handlers
Role-based access control using authorities

⚡ Best Practices Used

DTO pattern to avoid exposing entities
Clean separation of concerns
Proper package structuring
Secure password storage using BCrypt
.gitignore configured to exclude build files and secrets

🚧 Future Enhancements
- [ ] Payment gateway integration
- [ ] Swagger / OpenAPI documentation
- [ ] Product reviews and ratings
- [ ] Admin dashboard
- [ ] Pagination and filtering


👤 Author
Nayan Joshi
GitHub: https://github.com/nayan-9860
Email: nayanjoshi9860@gmail.com

⭐ If you find this project useful, feel free to star the repository!

 
