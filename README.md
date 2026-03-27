# NexusCart - Production-Ready E-Commerce Platform

A full-stack, production-grade e-commerce application built with **Spring Boot 3** (Java 21) and **React 18** (Vite). Designed with a modular monolith architecture ready for microservices evolution.

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)
![React](https://img.shields.io/badge/React-18-cyan)
![Security](https://img.shields.io/badge/Security-JWT+RBAC-red)

---

## 🚀 Features

### Core Functionality
- **Authentication & Authorization**: JWT-based stateless auth with Role-Based Access Control (ADMIN, CUSTOMER, VENDOR).
- **Product Catalog**: Hierarchical categories, advanced search, filtering, pagination, and sorting.
- **Shopping Cart**: Persistent cart management with real-time stock validation.
- **Order Management**: Complete order lifecycle (Pending → Shipped → Delivered) with transactional integrity.
- **Payment Integration**: Mock payment gateway with webhook support (ready for Stripe/Razorpay).
- **Image Handling**: Flexible file upload strategy (Local or Cloudinary).

### Technical Highlights
- **Security**: BCrypt password hashing, CORS hardening, Security Headers (CSP, X-Frame-Options), Input Sanitization.
- **Performance**: Redis caching ready, optimized DB queries with DTO projections, connection pooling (HikariCP).
- **Reliability**: Global exception handling, Flyway database migrations, Transactional service layers.
- **Developer Experience**: OpenAPI/Swagger documentation, Lombok, MapStruct, Hot-reload with Vite.
- **UI/UX**: Responsive Tailwind CSS design, Loading states, Toast notifications, Form validation, Protected routes.

---

## 🏗️ Architecture

### Backend (Spring Boot)
- **Pattern**: Modular Monolith with Hexagonal Architecture principles.
- **Packages**: `module_auth`, `module_product`, `module_cart`, `module_order`, `module_payment`.
- **Layers**: Controller → Service → Repository → Entity.
- **Database**: PostgreSQL/MySQL with Flyway version control.
- **Docs**: Swagger UI available at `/swagger-ui.html`.

### Frontend (React)
- **Build Tool**: Vite for fast development and optimized builds.
- **State Management**: React Context API + Custom Hooks (`useAuth`, `useCart`).
- **Styling**: Tailwind CSS for utility-first responsive design.
- **Routing**: React Router v6 with protected route wrappers.
- **HTTP Client**: Axios with interceptors for JWT injection and error handling.

---

## 🛠️ Tech Stack

| Component | Technology |
| :--- | :--- |
| **Backend Language** | Java 21 |
| **Backend Framework** | Spring Boot 3.2.x |
| **Security** | Spring Security 6, JWT (jjwt) |
| **Database** | MySQL 8.0 / PostgreSQL |
| **Migration** | Flyway |
| **Cache** | Redis (Configured) |
| **Frontend** | React 18 + Vite |
| **Styling** | Tailwind CSS |
| **API Docs** | SpringDoc OpenAPI |
| **Mapping** | MapStruct |
| **Utils** | Lombok, SLF4J |

---

## 📋 Prerequisites

- **JDK 21+**
- **Node.js 18+** & npm/yarn
- **Docker** (Optional, for DB/Redis)
- **MySQL/PostgreSQL** instance
- **Cloudinary Account** (Optional, for image hosting)

---

## ⚙️ Configuration

### 1. Database Setup
Create a database named `nexuscart` and update `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/nexuscart?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password
spring.flyway.enabled=true
```

### 2. JWT Security
Generate a strong secret key and update in `application.properties`:

```properties
app.jwt.secret=YOUR_VERY_LONG_SECURE_SECRET_KEY_HERE_MIN_64_CHARS
app.jwt.expiration-ms=86400000
```

### 3. Image Upload (Optional)
To use Cloudinary, add credentials to `application.properties`:

```properties
app.file.upload-type=cloudinary
cloudinary.cloud-name=your_cloud_name
cloudinary.api-key=your_api_key
cloudinary.api-secret=your_api_secret
```
*Default is `local`, storing images in `uploads/` directory.*

### 4. Frontend API URL
Update `frontend/.env`:

```env
VITE_API_BASE_URL=http://localhost:8080/api/v1
```

---

## 🚀 Getting Started

### Backend Setup

```bash
cd nexuscart-backend

# Compile and run
./mvnw spring-boot:run

# Access Swagger UI
# http://localhost:8080/swagger-ui.html
```

### Frontend Setup

```bash
cd nexuscart-frontend

# Install dependencies
npm install

# Start development server
npm run dev

# Access App
# http://localhost:5173
```

---

## 🔑 Default Credentials

Upon first run, the database migration creates default admin user:

- **Email**: `admin@nexuscart.com`
- **Password**: `admin123`
- **Role**: `ADMIN`

*Register new customers via the Signup page.*

---

## 📡 API Endpoints Overview

### Auth
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login and get JWT
- `GET /auth/profile` - Get current user profile

### Products (Public)
- `GET /products` - List products (paginated, sorted)
- `GET /products/{id}` - Product details
- `GET /products/search` - Search with filters

### Products (Admin)
- `POST /admin/products` - Create product
- `PUT /admin/products/{id}` - Update product
- `DELETE /admin/products/{id}` - Soft delete

### Cart & Orders
- `GET /cart` - Get user cart
- `POST /cart/items` - Add to cart
- `POST /orders/place` - Place order
- `GET /orders/my-orders` - Order history

---

## 🛡️ Security Features

1.  **Password Hashing**: BCrypt with strength 12.
2.  **Stateless Auth**: JWT tokens with expiration and refresh logic.
3.  **CORS**: Strictly configured for frontend origin only.
4.  **Headers**: HSTS, CSP, X-Content-Type-Options enabled.
5.  **Validation**: Strong input validation on all DTOs to prevent SQLi/XSS.
6.  **Rate Limiting**: Basic request throttling filter included.

---

## 📸 Screenshots

*(Add screenshots of Home, Product Detail, Cart, Admin Dashboard here)*

---

## 🤝 Contributing

1.  Fork the repository.
2.  Create a feature branch (`git checkout -b feature/AmazingFeature`).
3.  Commit changes (`git commit -m 'Add AmazingFeature'`).
4.  Push to branch (`git push origin feature/AmazingFeature`).
5.  Open a Pull Request.

---

## 📄 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Senior Software Architect**
*Building scalable, secure, and maintainable systems.*