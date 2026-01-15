# Theses Management System - Backend

Backend realizuje logikę serwerową systemu zarządzania procesem dyplomowania: udostępnia REST API do obsługi użytkowników i ról, prac/tematów (wraz z przypisaniami student–promotor–recenzent), elementów dokumentu (treść oraz załączniki), harmonogramu zadań i konsultacji. Zapewnia bezpieczeństwo w modelu **stateless** (Spring Security + JWT, kontrola dostępu RBAC) oraz komunikację „real‑time” (WebSocket/STOMP – czat, SSE – powiadomienia o zmianach). Dane są przechowywane w PostgreSQL (JPA/Hibernate), a API jest opisane w Swagger UI.

## Tech Stack
*   **Java 17** & **Spring Boot 3** – Szybkość i niezawodność.
*   **PostgreSQL** – Solidna baza danych.
*   **WebSocket (STOMP)** – Czat i powiadomienia live.
*   **Spring Security** – Bezpieczeństwo ról (Student, Promotor, Admin).
*   **Swagger UI** – Interaktywna dokumentacja API.

## Szybki Start

1.  **Baza Danych**: PostgreSQL na porcie `5432` (baza `postgres`, user `postgres`).
2.  **Uruchom**:
    ```bash
    mvn spring-boot:run
    ```
3.  **API** `http://localhost:8080`.

 **Dokumentacja API**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).
 **Frontend**: https://github.com/dolek122/theses-management




