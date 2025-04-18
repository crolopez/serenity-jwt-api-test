# Users management

This is a simple REST API project built with Spring Boot. Its main purpose is to serve as a testing ground for practicing use of `Serenity` for API and integration testing, but also `JWT` authentication with `Spring Security`.

## Main Features

The API exposes basic user management functionalities:

*   Registration of new users.
*   User authentication (login) using username and password.
*   Generation of JWT tokens upon successful authentication.
*   (Potentially) Basic CRUD operations for users (protected by authentication/roles).

## API Endpoints

The API exposes the following endpoints under the base path `/api/v1`:

*   **Authentication:**
    *   `POST /api/v1/auth/register`: Registers a new user. Requires `username`, `email`, `password`, `role`.
    *   `POST /api/v1/auth/login`: Authenticates a user. Requires `username`, `password`. Returns a JWT token upon success.
*   **User Management:**
    *   `GET /api/v1/users`: Gets all users (requires authentication, possibly ADMIN role).
    *   `POST /api/v1/users`: Creates a new user (requires authentication, possibly ADMIN role).
    *   `GET /api/v1/users/{id}`: Gets a user by ID (requires authentication, possibly ADMIN role).
    *   `PUT /api/v1/users/{id}`: Updates a user by ID (requires authentication, possibly ADMIN role).
    *   `DELETE /api/v1/users/{id}`: Deletes a user by ID (requires authentication, possibly ADMIN role).

## Prerequisites

*   JDK 17 or higher.
*   Maven 3.6+

## How to Launch the Application

1.  Clone this repository.
2.  Navigate to the project's root directory.
3.  Run the application using Maven and activate the `dev` profile (to load initial data from `data-dev.sql`):
    ```bash
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    ```
4.  Alternatively, you can run the `ApiApplication.java` class from your IDE, ensuring you configure the VM options to activate the `dev` profile: `-Dspring.profiles.active=dev`.
5.  The application will be available at `http://localhost:8080`.
6.  You can access the H2 console (if enabled in `application-dev.properties`) at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`, user: `sa`, password: `password` or as configured).

## How to Launch the Serenity Tests

1.  Ensure the application **is running** (see previous section).
2.  From the project's root directory, execute the following Maven command:
    ```bash
    mvn clean verify
    ```
3.  Maven will compile the code, run the Serenity BDD tests, and generate the report.
4.  The detailed Serenity report will be available at `target/site/serenity/index.html`. Open it in your browser to view the test results.

