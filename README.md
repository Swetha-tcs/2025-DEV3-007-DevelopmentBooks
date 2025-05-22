# 2025-DEV3-007-DevelopmentBooks

# 📘 Software Development Books Kata

This is a Spring Boot application that calculates the price for a basket of software development books. It applies discounts to combinations of different books. The application includes a REST API and a static HTML UI enhanced with JavaScript.

---

## 🔧 Features

- Calculates total price with discount rules.
- RESTful API (`/api/price`) accepts a list of book titles as JSON.
- Static HTML UI with JavaScript dynamically posts to the API.
- Built using Test-Driven Development (TDD) : Red → Green → Refactor.

---

## 📁 Project Structure

```
software-books/
├── src/
│   ├── main/
│   │   ├── java/com/development/softwarebooks/
│   │   │   ├── controller/
│   │   │   │   └── PricingController.java
│   │   │   ├── domain/
│   │   │   │   ├── Book.java
│   │   │   │   └── Basket.java
│   │   │   ├── service/
│   │   │   │   └── PricingService.java
│   │   │   └── SoftwareBooksApplication.java
│   │   └── resources/
│   │       └── static/
│   │           └── index.html
│   │           └── style.css
│   ├── test/
│       └── java/com/development/softwarebooks/
│           └── service/
│               └── PricingServiceTest.java
```

---

## 🌐 API Endpoint

### `POST /api/price`

- **Request Body (JSON Array):**
```json
["Clean Code", "The Clean Coder", "Clean Architecture", "Test-Driven Development by Example","Working Effectively with Legacy code"]
```

- **Response:**
```json
95.0
```

- **Consumes:** `application/json`
- **Returns:** `application/json`

---

## 💻 Frontend (Static HTML)

`index.html` is a plain HTML page with embedded JavaScript that gathers form input and sends a POST request as JSON to the backend:


## 🧮 Discount Rules

- 1 book: no discount
- 2 different books: 5% discount
- 3 different books: 10% discount
- 4 different books: 20% discount
- 5 different books: 25% discount

These rules are applied greedily by grouping the most unique books together in each round.

---


## 📎 Supported Book Titles

- `"Clean Code"`
- `"The Clean Coder"`
- `"Clean Architecture"`
- `"Test Driven Development by Example"`
- `"Working Effectively With Legacy Code"`

---
