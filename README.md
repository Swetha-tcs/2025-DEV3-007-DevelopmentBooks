# 2025-DEV3-007-DevelopmentBooks

# 📚 Software Development Books 

This is a **Spring Boot** application that calculates the total price for a basket of software development books. The pricing logic applies discounts for combinations of *different* book titles. The application is built using **TDD (Test-Driven Development)** principles and includes:

- ✅ **RESTful API for programmatic price calculation**
- ✅ **Static HTML and JavaScript-based user interface**
- ✅ **Comprehensive unit and integration testing**
- ✅ **Clean, modular architecture with a testable service layer**

---

## 🔍 Problem Overview

There are 5 different software development books available:

1. Clean Code  
2. The Clean Coder  
3. Clean Architecture  
4. Test-Driven Development by Example  
5. Working Effectively with Legacy Code

💰 **Base Price per Book**: €50, with the following discounts applied when purchasing different titles together:

| Unique Titles in Set | Discount   |
|----------------------|------------|
| 1                    | 0%         |
| 2                    | 5%         |
| 3                    | 10%        |
| 4                    | 20%        |
| 5                    | 25%        |

The challenge is to **minimize the total price** for any basket of books by optimally grouping them to take advantage of the best possible discounts.

📄 Problem description :  
👉 [Development Books – stephane-genicot.github.io](https://stephane-genicot.github.io/DevelopmentBooks.html)

---

## 🚀 Features

- 🧠 Optimal discount calculation using memoization  
- 🧪 TDD-based backend development with JUnit & Mockito  
- 🌐 REST API for programmatic price calculation  
- 💻 Interactive web frontend using HTML + JavaScript  
- 🔒 Input validation and error handling  

---

## 🛠️ Technology Stack

- Java 
- Spring Boot 3.x
- Maven
- JUnit 5, Mockito
- HTML/CSS/JavaScript (Vanilla)
- RESTful API

---

## 📁 Project Structure

```
software-books/
├── src/
│   ├── main/
│   │   ├── java/com/development/softwarebooks/
│   │   │   ├── SoftwareBooksApplication.java
│   │   │
│   │   │   ├── controller/
│   │   │   │   └── PricingController.java
│   │   │
│   │   │   ├── domain/
│   │   │   │   ├── Book.java
│   │   │   │   └── Basket.java
│   │   │
│   │   │   ├── service/
│   │   │   │   └── PricingService.java
│   │   │
│   │   │   └── config/
│   │   │       └── DiscountProperties.java
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── static/
│   │           ├── index.html
│   │           ├── script.js
│   │           └── style.css
│
├── src/
│   └── test/
│       └── java/com/development/softwarebooks/
│           ├── service/
│           │   ├── PricingServiceTest.java
│           │   └── DiscountCalculatorTest.java
│           │
│           └── controller/
│               └── PricingControllerTest.java
│
├── pom.xml
└── README.md
```

---

## 🌐 API Endpoint

### `POST /api/price`

- **Request Body (JSON Array):**
```json
["Clean Code", "The Clean Coder", "Clean Architecture", "Test-Driven Development by Example","Working Effectively with Legacy Code"]
```

- **Response:**
```json
95.0
```

- **Consumes:** `application/json`
- **Returns:** `application/json`

---

## 💻 Frontend (Static HTML)

`index.html` is a plain HTML page with embedded JavaScript that collects user input and sends it to the backend.


---

## 🧪 Test-Driven Development & Running the Application

This project was developed using TDD (Test-Driven Development) principles:

      1. Write a failing unit test

      2. Implement just enough code to make it pass

      3. Refactor with confidence

▶️ **How to Run**

From your IDE (IntelliJ / Eclipse / VS Code):

✅ **Run Unit Tests to verify pricing logic:**

Locate PricingServiceTest.java → Right-click → Run

🚀 **Start the Application:**

Locate SoftwareBooksApplication.java → Right-click → Run

🌐 **Access the Web App:**

Open your browser and go to 👉 [http://localhost:8080](http://localhost:8080/)

---

 ### 💡 Example

**Input:**
- 2× Clean Code  
- 2× The Clean Coder  
- 2× Clean Architecture  
- 1× Test-Driven Development by Example  
- 1× Working Effectively with Legacy Code  

**Optimal Grouping:**  
→ 2 sets of 4 different books (20% discount each)

**Calculation:**  
2 × (4 × €50 × 0.80) = **€320.00**
