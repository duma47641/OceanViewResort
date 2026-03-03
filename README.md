**Ocean View Resort - Advanced Reservation Management System**

This repository contains a high-performance, distributed web application for managing hotel reservations. The system focuses on architectural integrity, utilizing advanced Java design patterns and automated testing frameworks to ensure scalability and reliability.


**Architectural Overview (Advanced Programming Focus)**
The application follows a strict Model-View-Controller (MVC) architecture, ensuring a clean separation of concerns and maintainable code.

Implemented Design Patterns
Singleton Pattern: Centralized database connection management via DBConnection to optimize resource utilization.

Data Access Object (DAO) Pattern: Decouples the business logic from the persistence layer, allowing for flexible database interactions.

Factory Pattern: Abstracted object creation logic to enhance system extensibility.

**Technical Features**
Robust Backend: Built with Java Servlets and JSP, running on Apache Tomcat 10.x.

Persistence Layer: Integrated with MySQL via WampServer, handling complex relational data for guests and room types.

Automated Billing: Utilizes the iText library to programmatically generate professional PDF invoices directly from database records.

Session Management: Secure server-side session tracking to maintain user state across the distributed environment.

**Advanced Testing & Automation**
In alignment with Test-Driven Development (TDD) principles, the system includes a comprehensive suite of automated and manual tests.

Unit Testing (JUnit 5): Automated verification of core logic.

Verified the DBConnection Singleton instance to ensure it is not null and remains thread-safe.

Result: 100% pass rate with "Green Bar" confirmation in IntelliJ IDEA.

End-to-End Validation: Successfully processed real-world guest reservations (e.g., Guest: Virajini) from UI entry to database persistence.

**Setup and Installation**
Prerequisites
Java SDK 21+

IntelliJ IDEA (Ultimate or Community)

WampServer (with MySQL 8.x)

Apache Tomcat 10.1+


**System Evidence**
Database Connectivity

Successful Reservation

Live Guest List

Unit Test Pass
