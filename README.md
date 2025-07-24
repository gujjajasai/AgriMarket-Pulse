# AgriMarket Pulse: Real-Time Agricultural Commodity Price Tracking & Analysis


## 💡 Problem Statement

In developing economies like India, agricultural commodity prices can fluctuate significantly across different markets (mandis) even within the same region on a given day. Farmers often lack real-time visibility into these disparities, preventing them from selling their produce at optimal prices. Similarly, small traders miss out on potential arbitrage opportunities. This information asymmetry leads to economic inefficiency and impacts farmer livelihoods.

## ✨ Solution

**AgriMarket Pulse** is a microservices-based application designed to address this problem by providing real-time agricultural commodity price tracking, analysis, and alerting. It aims to empower farmers and traders with crucial market intelligence, enabling more informed decisions.

## 🚀 Key Features

*   **Automated, Dynamic Web Scraping:**
    *   Utilizes **Jsoup** to periodically fetch live, public agricultural commodity prices from `Agmarknet.gov.in`.
    *   **Configurable and dynamic** scraping across multiple commodities (e.g., Onion, Potato, Tomato), states (Maharashtra, Uttar Pradesh), and districts.
*   **Microservices Architecture:**
    *   **`market-data-collector`:** A dedicated service responsible for data acquisition and forwarding.
    *   **`price-analysis-service`:** A robust backend API for data ingestion, storage, querying, and analysis.
*   **Robust Data Pipeline:**
    *   **Real-time Data Ingestion:** Processes incoming raw data as it's scraped.
    *   **Data Cleansing & Transformation:** Converts raw string prices to `BigDecimal` for accurate financial calculations and date strings to `LocalDate`, with comprehensive error handling for invalid formats.
    *   **Data Integrity:** Implements **unique constraints** on key fields (date, commodity, market) and handles **duplicate entries gracefully** during ingestion.
*   **RESTful API:**
    *   Provides a clean and intuitive API for querying commodity prices by various criteria (e.g., all commodities, by specific commodity, by commodity-market-date combination).
*   **Advanced Price Analysis:**
    *   Offers a dedicated endpoint to calculate **arbitrage potential** by identifying the overall minimum and maximum prices for a given commodity across all markets on a specific date, along with the price difference.
*   **Automated Alerting System:**
    *   A scheduled task (within `price-analysis-service`) periodically monitors for significant price disparities (arbitrage opportunities) exceeding a predefined threshold, logging them as alerts. (Extendable to email/SMS notifications).
*   **Containerization with Docker:**
    *   Both backend microservices are **Dockerized** for consistent, portable, and isolated environments.
    *   **Docker Compose** orchestrates the entire backend stack (two services + PostgreSQL database).
*   **Simple Web Frontend:**
    *   A basic HTML/CSS/JavaScript interface provides a user-friendly way to interact with the backend APIs and visualize the collected data and analysis results.

## 🛠️ Technologies Used

**Backend (Java Microservices):**
*   **Java 21/17** (JDK)
*   **Spring Boot 3.x**
*   **Spring Web:** Building RESTful APIs.
*   **Spring Data JPA:** Database interaction and ORM.
*   **PostgreSQL:** Relational database for persistent storage.
*   **Jsoup:** HTML parsing and web scraping.
*   **Lombok:** Boilerplate code reduction.
*   **Maven:** Project build automation.

**Deployment & Infrastructure:**
*   **Docker:** Containerization of microservices.
*   **Docker Compose:** Multi-container orchestration for local development.
*   **Git & GitHub:** Version control and collaboration.

**Frontend:**
*   **HTML5, CSS3, JavaScript:** Basic web interface.

## 🏗️ Architecture Diagram

+---------------------------+ +-------------------------------+ +-------------------+
| | | | | |
| Agmarknet.gov.in | | market-data-collector | | price-analysis-service |
| (External Data Source) | ----> | (Spring Boot, Jsoup) | ----> | (Spring Boot, JPA, Postgres) |
| | | - Scrapes daily price data | | - Ingests & transforms data |
| | | - Sends data via HTTP POST | | - Stores in PostgreSQL |
+---------------------------+ +-------------------------------+ | - Exposes REST API |
| - Runs price analysis |
| - Triggers alerts |
+----------^----------------+
|
| (HTTP Requests)
|
+----------+----------------+
| |
| agrimarket-frontend |
| (HTML, CSS, JavaScript) |
| - User Interface |
| - Queries Backend APIs |
+---------------------------+



## 🚀 Getting Started (Local Development)

These instructions will get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

*   **Java Development Kit (JDK) 17 or 21/22:** Ensure `JAVA_HOME` is set correctly.
*   **Apache Maven 3.6.3+:** Ensure `mvn` command is accessible from your PATH.
*   **Docker Desktop:** Running and configured with WSL 2/Hyper-V enabled.
*   **Git:** For cloning the repository.

### Setup Steps

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/YOUR_USERNAME/AgriMarket-Pulse.git
    cd AgriMarket-Pulse
    ```
    (Replace `YOUR_USERNAME` with your GitHub username)

2.  **Configure Database Password (Securely):**
    *   Create a `.env` file in the **root directory** of this `AgriMarket-Pulse` folder (the same directory as `docker-compose.yml`).
    *   Add your PostgreSQL password to it:
        ```
        # .env
        DB_PASSWORD=your_secure_postgres_password
        ```
    *   **Ensure this `.env` file is in your `.gitignore`** (it should be if you used the provided `.gitignore`).

3.  **Build Spring Boot Application JARs:**
    *   Navigate to the `price-analysis-service` directory and build:
        ```bash
        cd price-analysis-service
        mvn clean package -DskipTests
        cd .. # Go back to AgriMarket-Pulse root
        ```
    *   Navigate to the `market-data-collector` directory and build:
        ```bash
        cd market-data-collector
        mvn clean package -DskipTests
        cd .. # Go back to AgriMarket-Pulse root
        ```
    *(Note: `-DskipTests` is used to bypass test failures if any, which might be related to database setup in test environment.)*

4.  **Run Backend Services with Docker Compose:**
    *   Ensure Docker Desktop is running.
    *   Ensure your local PostgreSQL service (if you have one installed outside Docker) is stopped to avoid port conflicts.
    *   From the `AgriMarket-Pulse` root directory (where `docker-compose.yml` is), run:
        ```bash
        docker-compose up --build
        ```
        This will build Docker images for your services and start PostgreSQL, `price-analysis-service`, and `market-data-collector`.

5.  **Serve Frontend Locally:**
    *   Open a **new** terminal/command prompt.
    *   Navigate to the `agrimarket-frontend` directory:
        ```bash
        cd agrimarket-frontend
        ```
    *   Start a simple Python HTTP server:
        ```bash
        python -m http.server 8000
        ```

6.  **Access the Application:**
    *   Open your web browser and go to: `http://localhost:8000`

### Initial Data & Testing

*   The `market-data-collector` will attempt to scrape data from `Agmarknet.gov.in` periodically. Due to the dynamic nature of the external website, it might return "No Data Found" on certain days/queries.
*   To test the full functionality (analysis, alerting, queries), **it is recommended to manually ingest sample data** using Postman:
    *   Make a `POST` request to `http://localhost:8082/api/prices/ingest`.
    *   Set `Content-Type: application/json` header.
    *   Use a JSON body with sample `MarketDataDTO` records (e.g., for "Onion (Common)" across two different markets with price disparity).
    *   Observe logs in the `docker-compose` terminal for successful ingestion and alerts.
    *   Then, use the frontend UI or Postman to query (`http://localhost:8082/api/query/commodities`, `/api/query/analysis/commodity`, etc.).



## 🤝 Contributing

Feel free to fork this repository, contribute, and enhance the project.



Gujjala Gnaneswara Rao.