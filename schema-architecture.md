# Application Architecture Documentation

## Section 1: Architecture Summary

This Spring Boot application implements a hybrid architecture that combines traditional MVC patterns with modern REST API design. The application serves two distinct user interfaces: server-side rendered dashboards for Admin and Doctor users utilizing Thymeleaf templating, and client-side applications for Appointments and Patient interactions that consume JSON REST APIs. The backend employs a dual-database strategy to optimize data storage and retrieval—MySQL handles structured relational data for core entities like patients, doctors, appointments, and admin records, while MongoDB manages document-based prescription data that benefits from flexible schema requirements.

The application follows a layered architecture pattern where all incoming requests, whether from Thymeleaf controllers or REST endpoints, are processed through a unified service layer. This service layer acts as the business logic hub and coordinates with the appropriate data access repositories. The data persistence layer is split between Spring Data JPA repositories for MySQL operations and MongoDB repositories for document storage, ensuring each database technology is utilized for its strengths while maintaining clean separation of concerns throughout the application stack.

## Section 2: Numbered Flow of Data and Control

1. **User Interface Access**: Users access either the AdminDashboard/DoctorDashboard (web pages) or Appointments/PatientDashboard modules (client applications).

2. **Controller Routing**: Requests are routed to the appropriate controller type—Thymeleaf controllers handle dashboard page requests while REST controllers process JSON API calls from client applications.

3. **Service Layer Processing**: Both controller types delegate business logic to a common service layer that contains the core application logic and orchestrates data operations.

4. **Repository Selection**: The service layer determines which repository to use based on the data type—MySQL repositories for patient, doctor, appointment, and admin data, or MongoDB repository for prescription data.

5. **Data Model Mapping**: MySQL repositories work with JPA entities that map to relational database tables, while MongoDB repository handles document models for flexible prescription data storage.

6. **Database Interaction**: The repositories execute the actual database operations—MySQL database for structured queries and MongoDB database for document operations.

7. **Response Generation**: Results flow back through the same path, with Thymeleaf controllers rendering HTML pages and REST controllers returning JSON responses to client applications.

