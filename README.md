# Mottu Location - Web Application

## Integrantes
* **RM555317** - Fernando Fontes
* **RM556814** - Guilherme Jardim

---

## 🚀 About the Project

This is a complete **Web Application** developed with **Java and Spring Boot** for managing Mottu's motorcycle fleet. The system allows for the control of motorcycles and sensors, as well as logging their movements, all through a secure and intuitive web interface.

This project was developed for the "Advanced Java Development" course and meets all the required technical specifications, including:
* **Thymeleaf:** For the view layer (frontend).
* **Flyway:** For version control of the PostgreSQL database.
* **Spring Security:** For form-based authentication and role-based access control.

---

## 🏛️ Architecture

The project uses a **Layered Architecture** to ensure a clear separation of concerns:

* **`Controller`:** The layer responsible for receiving HTTP requests from the web interface (`@Controller`) and the REST API (`@RestController`).
* **`Service`:** The layer that contains the primary business logic and orchestrates operations.
* **`Repository`:** The data access layer, using Spring Data JPA.
* **`Entity`:** The layer that represents the database tables.

---

## 🛠️ Technologies and Features

* **Backend:** Java 17, Spring Boot, Spring Security
* **Frontend:** Thymeleaf, HTML5, CSS3
* **Database:** PostgreSQL (running on Docker) with schema management via **Flyway**
* **Authentication:** Login and logout system with user roles (`ADMIN`, `USER`)
* **Web Functionality:** Complete CRUD for Motorcycles and Sensors with data validation.
* **Business Logic:** Automatic RFID generation, movement logging.

---

## 🏁 Getting Started: How to Run

### Prerequisites
* **JDK 17** or higher
* **Maven 3.8** or higher
* **Docker Desktop** (must be running)

### Step-by-Step
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/xfnd25/motolocation.git](https://github.com/xfnd25/motolocation.git)
    cd motolocation
    ```
2.  **Start the Database Container:**
    Open a terminal in the project's root directory and run the following command to start the PostgreSQL database:
    ```bash
    docker-compose up -d
    ```
3.  **Run the Spring Boot Application:**
    You can run the application directly from your IDE by running the `MotolocationApplication.java` file, or by using the following Maven command in the terminal:
    ```bash
    ./mvnw spring-boot:run
    ```

### Access and Credentials
* Access the application at: **[http://localhost:8080](http://localhost:8080)**
* You will be redirected to the login screen. Use the following credentials:
    * **Administrator:**
        * Username: `admin`
        * Password: `admin`
    * **Common User:**
        * Username: `user`
        * Password: `user`

---

## 🗄️ Flyway Migrations Structure

The database is versioned using Flyway. The migrations are organized as follows:
* **V1:** Creates the `MOTO` table.
* **V2:** Creates the `SENSOR` and `MOVIMENTACAO` tables.
* **V3:** Creates the `USERS` table for the security system.
* **V4:** Inserts the initial users (`admin` and `user`) and a sample motorcycle.

---

With this `README.md`, your project is now complete and well-documented. Congratulations on finishing!
