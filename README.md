# Audit Service

**Offeria — a product by [Al‑Wahha Al‑Sehriya](https://github.com/Al-Wahha-Al-Sehriya).**

[Company website](https://wahasehriya.com/) · [Offeria repositories](https://github.com/offeria-io)

## Description
The Audit Service is responsible for logging and managing audit trials across the Offeria platform. It consumes audit events from Kafka and stores them in a database for compliance and tracking purposes.

## Architecture Diagram
```mermaid
graph TD
    Services[Microservices] -->|Publish Audit Events| Kafka[(Kafka)]
    Kafka -->|Consume| AS[Audit Service]
    AS -->|Store| DB[(PostgreSQL)]
    Client[Admin/Client] -->|Query Audit Logs| AS
```

## File Structure
```text
audit-service/
├── k8s/                  # Kubernetes manifests
├── src/
│   ├── main/
│   │   ├── java/offeria/audit_service/
│   │   │   ├── controller/  # REST endpoints
│   │   │   ├── dto/         # Data Transfer Objects
│   │   │   ├── exception/   # Custom exceptions
│   │   │   ├── mapper/      # Object mapping logic
│   │   │   ├── messaging/   # Messaging logic (Kafka)
│   │   │   ├── model/       # Data entities
│   │   │   ├── repository/  # Data access layer
│   │   │   └── service/     # Business logic & Consumers
│   │   └── resources/       # Application configuration
│   └── test/                # Unit and Integration tests
├── Dockerfile           # Docker build instructions
└── pom.xml              # Maven dependencies
```

## Technologies
- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Kafka**
- **PostgreSQL**
- **Maven**

## Key Dependencies
- `spring-boot-starter-data-jpa`: Database interaction.
- `spring-kafka`: Messaging with Apache Kafka.
- `spring-cloud-starter-netflix-eureka-client`: Service registration.

## Environment Variables
- `SPRING_PROFILES_ACTIVE`: Active profile.
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Discovery Service URL.
- `DB_URL`: JDBC URL for PostgreSQL.
- `DB_USERNAME`: PostgreSQL username.
- `DB_PASSWORD`: PostgreSQL password.
- `KAFKA_SERVERS`: Kafka bootstrap servers.
