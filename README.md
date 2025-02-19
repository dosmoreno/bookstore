# Bookstore API

This is a RESTful API for managing a bookstore. It allows customers to place orders, manage books, and track loyalty points. The API is built with Spring Boot, using H2 in-memory database for simplicity during development.

## Table of Contents
1. [Overview](#overview)
2. [Technologies Used](#technologies-used)
3. [Setup and Running the Project](#setup-and-running-the-project)
4. [API Endpoints](#api-endpoints)
5. [Database Schema](#database-schema)
6. [Testing](#testing)
7. [Decisions Made](#decisions-made)

## Overview

The application allows customers to:

- View all available books.
- Place an order consisting of books.
- Manage loyalty points based on their orders.

The API handles the creation of orders and calculates pricing using different strategies depending on the type of book (e.g., regular, old edition).

## Technologies Used

- **Spring Boot**: The main framework used for building the backend.
- **H2 Database**: In-memory database for quick development and testing.
- **JUnit & Mockito**: Used for writing unit and integration tests.
- **ModelMapper**: Used for mapping between DTOs and entities.
- **Spring Web**: For creating REST APIs.

## Setup and Running the Project

1. **Clone the Repository**

   First, clone the repository to your local machine.

   ```bash
   git clone https://github.com/dosmoreno/bookstore.git
   cd bookstore-api
   ```

2. **Install Dependencies**

   Make sure you have [Java 17+](https://adoptopenjdk.net/) and [Maven](https://maven.apache.org/) installed.

   Run the following command to install dependencies:

   ```bash
   mvn clean install
   ```

3. **Run the Application**

   To run the application locally, use the following command:

   ```bash
   mvn spring-boot:run
   ```

   This will start the application on the default port (`8080`).

4. **Access the API**

   Once the application is running, you can access the API at:

   ```
   http://localhost:8080
   ```

   Use any HTTP client (e.g., Postman, Insomnia) to interact with the API.

## API Endpoints

### 1. **Get All Books**

   **GET** `/books`

   Returns a list of all available books.

   **Response:**
   ```json
   [
       {
           "id": 1,
           "title": "Book 1",
           "author": "Author 1",
           "price": 29.99,
           "type": "REGULAR"
       },
       ...
   ]
   ```

### 2. **Place an Order**

   **POST** `/orders`

   This endpoint allows a customer to place an order by providing a list of `bookIds`.

   **Request Body:**
   ```json
   {
       "customerId": 1,
       "bookIds": [1, 2]
   }
   ```

   **Response:**
   ```json
   {
       "totalPrice": 59.98
   }
   ```

### 3. **Get Loyalty Points**

   **GET** `/customers/loyalty/{customerId}`

   This endpoint returns the loyalty points for a given customer.

   **Response:**
   ```json
   {
       "loyaltyPoints": 10
   }
   ```

## Database Schema

The database schema consists of the following tables:

1. **Book**
   - `id`: Unique identifier for the book.
   - `title`: Title of the book.
   - `author`: Author of the book.
   - `price`: Price of the book.
   - `type`: Type of book (e.g., Regular, Old Edition).

2. **Customer**
   - `id`: Unique identifier for the customer.
   - `name`: Name of the customer.
   - `loyaltyPoints`: Points accumulated by the customer.

3. **Order**
   - `id`: Unique identifier for the order.
   - `customer_id`: Foreign key referencing the `Customer`.
   - `totalPrice`: Total price of the order.

4. **OrderItem**
   - `id`: Unique identifier for the order item.
   - `order_id`: Foreign key referencing the `Order`.
   - `book_id`: Foreign key referencing the `Book`.
   - `quantity`: Quantity of the book in the order.
   - `price`: Price for that quantity.

### Relationships:
- **Customer ↔ Order**: A customer can place multiple orders.
- **Order ↔ OrderItem**: An order can have multiple books.
- **Book ↔ OrderItem**: A book can appear in multiple orders.

## Testing

### Unit Tests

Unit tests are included to ensure the correctness of the service layer (e.g., `OrderService`) and controller layer (e.g., `OrderController`).

1. **Run tests**:

   ```bash
   mvn test
   ```

2. **Mocking and Assertions**:
   - `Mockito` is used to mock service dependencies.
   - `JUnit` is used to perform assertions and verify results.

### Controller Tests

Tests are also included for testing the controller methods (`GET`, `POST` requests) to ensure correct behavior when interacting with the API.

## Decisions Made

### 1. **Spring Boot and H2 Database**:
   - **Spring Boot**: Chosen for its simplicity in building REST APIs and its rich ecosystem of tools for testing and managing dependencies.
   - **H2 Database**: Used for simplicity in development and testing. This can easily be switched to another database (e.g., MySQL or PostgreSQL) if required.

### 2. **Pricing Strategy Pattern**:
   - A **Strategy pattern** was used to handle different pricing strategies for different book types (e.g., Regular, Old Edition). This decouples the logic for pricing, making the code easier to extend in the future if more book types are added.

### 3. **DTOs and ModelMapper**:
   - **DTOs (Data Transfer Objects)** were used to abstract the internal data models (e.g., `Book`, `Customer`) from the API response. This allows easier modification of the internal structure without affecting the API contract.
   - **ModelMapper** is used for object-to-object mapping, simplifying conversion between entities and DTOs.

### 4. **Error Handling**:
   - A global exception handler (`@RestControllerAdvice`) was used to catch all exceptions and provide meaningful error messages to the API consumer. This ensures a consistent error response format.

### 5. **Unit Testing**:
   - Tests were written using `JUnit` and `Mockito` to ensure the correctness of the service methods and controllers. This helps prevent regressions and ensures the logic behaves as expected.
   - Tests mock external dependencies like the database, allowing unit tests to focus on the business logic.