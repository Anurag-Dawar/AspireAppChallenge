```markdown
# Aspire Loan API

## Introduction

This is a RESTful API for managing loan applications in the Aspire app. It allows authenticated users to apply for loans, view loan details, and manage repayments.

## Features

- User authentication with JWT tokens
- Apply for a loan
- View loan details
- Approve loan applications (admin only)
- Submit loan repayments
- Weekly repayment frequency

## Technologies Used

- Java
- Spring Boot
- Spring Security
- MySQL
- JSON Web Tokens (JWT)
- Maven

## Setup
   ```
1. **Clone the repository:**
   ```bash
   git clone https://github.com/Anurag-Dawar/AspireAppChallenge.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd AspireAppChallenge
   ```

3. **Configure the application properties:**

   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/aspire
   spring.datasource.username=root
   spring.datasource.password=
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver


4. **Build the project:**

   ```bash
   mvn clean install
   ```

5. **Run the application:**

   ```bash
   java -jar target/aspire-loan-api.jar
   ```

## API Endpoints

- **POST /api/loan/create:** Apply for a loan
- **GET /api/loan/{loanId}:** View loan details
- **PUT /api/loan/approve/{loanId}:** Approve a loan application (admin only)
- **POST /api/repayment/add:** Submit a loan repayment

## Authentication

The API uses JSON Web Tokens (JWT) for authentication. To obtain a token, send a POST request to `/api/auth/login` with valid credentials. Include the token in the `Authorization` header for authenticated requests.

## Usage

To interact with the API, you can use tools like Postman. Here's an example workflow:
Here is the link for API Collection :- https://documenter.getpostman.com/view/15920060/2sA3JGg4Ej

1. Authenticate with valid credentials to obtain a JWT token.
2. Use the token in the `Authorization` header for subsequent requests.
3. Apply for a loan using the `/api/loan/create` endpoint.
4. View loan details with the `/api/loan/{loanId}` endpoint.
5. Approve loan applications (admin only) using the `/api/loan/approve/{loanId}` endpoint.
6. Submit loan repayments with the `/api/repayment/add` endpoint.

## Testing Users

For testing purposes, the following users are available:

- **Admin User:**
  - Username: ADMIN@Aspire
  - Password: ADMIN@123

- **Customer User 1:**
  - Username: CUSTOMER1@Aspire
  - Password: CUSTOMER@1

- **Customer User 2:**
  - Username: CUSTOMER2@Aspire
  - Password: CUSTOMER@2

You can use these usernames and passwords to generate JWT tokens and authenticate requests.
```

## Contributors

- Anurag Dawar
