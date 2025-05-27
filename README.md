# Retail Pricing Service

This is a RESTful API built with Spring Boot that allows clients to query product pricing information based on application date, product ID, and brand ID. The service calculates the applicable price based on defined pricing rules, including validity periods and priority handling.

---

## 🔧 Technologies Used

- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- H2 In-Memory Database
- Lombok
- REST API (Spring Web)
- JUnit

---

## 🚀 Features

- Query pricing information based on:
    - Application date and time
    - Product ID
    - Brand ID
- Returns the applicable price, price list, and valid date range
- Uses priority to resolve conflicts when multiple price ranges overlap
- Database is initialized automatically at startup using H2 and `src/main/resources/data.sql`
- Includes integration tests for all main business scenarios

---

## 📦 API Endpoint

### `GET /api/applicablePrice`

**Query Parameters:**
- `dateTime` (e.g., `2020-06-14T10:00:00`)
- `productId` (e.g., `35455`)
- `brandId` (e.g., `1`)

**Example request:**
```
GET /api/applicablePrice?dateTime=2020-06-14T10:00:00&productId=35455&brandId=1
```

**Example response:**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```

---

## 🛠 How to Run the Project

1. **Clone the repository**

```bash
git clone https://github.com/crisansa4/retail-inventory-api_02.git
cd retail-pricing-service
```

2. **Build and run the application**

```bash
./mvnw spring-boot:run
```

3. **Access H2 Console (for dev/debug)**

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:retaildb`
- Username: `sa`
- Password: *(leave blank)*

---

## 📂 Project Structure (Hexagonal Architecture)

```
src/
├── domain/           # Business logic & core models
├── application/      # Use cases and orchestration
├── infrastructure/   # REST controllers, DB access
├── resources/        # Configuration files, data.sql
```

---

## ‍💻 Author

Daiana Juárez  
[LinkedIn](https://linkedin.com/in/daiana-juarez)

---

## 📃 License

This project is for evaluation purposes only and is not intended for production use.
