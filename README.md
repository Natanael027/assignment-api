# Assignment API

This project is an assignment to develop a RESTful API using Java Spring Boot. The API is divided into three modules, each serving a specific purpose:

---

## Modules and Endpoints

### 1. Membership Module (Public & Private)
This module provides APIs for creating and managing user information.
#### Endpoints:
- **`/registration`**: User registration.
- **`/login`**: User login.
- **`/profile`**: Retrieve user profile information.
- **`/profile/update`**: Update user profile details.
- **`/profile/image`**: Manage user profile images.

---

### 2. Information Module (Public)
This module provides APIs for displaying general information and services.
#### Endpoints:
- **`/banner`**: Fetch banner information.
- **`/service`**: Fetch service details.

---

### 3. Transaction Module (Private)
This module provides APIs for handling user transactions.
#### Endpoints:
- **`/balance`**: Check user account balance.
- **`/topup`**: Add balance to the user account.
- **`/transaction`**: Perform a transaction.
- **`/transaction/history`**: Retrieve transaction history.

---

## System Requirements

- **Java Version**: Java 21 or a compatible version required by the Spring Boot framework.
- **Database Server**: MySQL (configured as per project requirements).

---

## Database Setup

To set up the MySQL database for this project, follow these steps:
- Execute DDL.sql file to create database and tables.
- Execute DML.sql file to insert data into created tables.

