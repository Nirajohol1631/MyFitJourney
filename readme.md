My-Fit-Journey: A Gym Management System Backend 🏋️‍♀️
Welcome to the backend for My-Fit-Journey, a comprehensive gym management system. This project is built using Spring Boot and provides a secure, robust, and scalable API for managing gym operations, including user authentication, class scheduling, and membership management.

✨ Key Features
Role-Based Access Control: Manages user roles for Admins, Trainers, and Members.

Secure Authentication: Implements a secure JWT-based authentication system for API access.

User Management: Endpoints for user registration and login.

Class Scheduling: Trainers can create, manage, and view fitness class sessions.

Class Booking: Members can browse and book upcoming classes.

Membership Plans: Admins can define and manage gym membership plans with different prices and durations.

User Plan Management: Members can purchase plans, and their active plans are tracked.

Financial Tracking: The system includes entities to track revenue from plans and expenses like trainer salaries.

🛠️ Technology Stack
Backend Framework: Spring Boot

Security: Spring Security, JSON Web Tokens (JWT)

Database: MySQL

Object-Relational Mapping (ORM): Spring Data JPA with Hibernate

Dependencies: Lombok for boilerplate code reduction, spring-boot-starter-validation for data validation.

🚀 Getting Started
Prerequisites
To get this project up and running on your local machine, you'll need the following installed:

Java 21+

Maven 3.6+

MySQL 8.0+

An API client like Postman or Insomnia to test the endpoints.

Installation & Setup
Clone the repository:

git clone https://github.com/your-username/my-fit-journey-backend.git
cd my-fit-journey-backend

Database Configuration:
Create a new MySQL database. Open the src/main/resources/application.properties file and update the database connection details.

spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

The ddl-auto=update setting will automatically create the database tables on application startup.

Run the Application:
You can run the application directly from your IDE or use the Maven command line.

mvn spring-boot:run

The application will start on http://localhost:8080.

🔑 API Endpoints
All endpoints are protected by JWT authentication, with the exception of the /api/auth/ routes.

Authentication
Register a new user:
POST /api/auth/register

Example Request Body:

{
  "username": "johndoe",
  "password": "password123",
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "role": "MEMBER"
}

Available roles: ADMIN, TRAINER, MEMBER

Log in and get a JWT token:
POST /api/auth/login

Example Request Body:

{
  "username": "johndoe",
  "password": "password123"
}

Example Response:

{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}

You must include this token in the Authorization header of all subsequent protected requests as a Bearer token.

Protected Routes
GET /api/plans: View all available plans.

POST /api/plans: Create a new plan (Admin only).

POST /api/classes: Create a new class session (Trainer only).

POST /api/bookings/class: Book a class session (Member only).

POST /api/bookings/plan: Book a plan (Member only).

🤝 Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

Fork the Project

Create your Feature Branch (git checkout -b feature/AmazingFeature)

Commit your Changes (git commit -m 'feat: Add some AmazingFeature')

Push to the Branch (git push origin feature/AmazingFeature)

Open a Pull Request

📄 License
Distributed under the MIT License. See LICENSE for more information.