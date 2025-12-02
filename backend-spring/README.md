# Spring Boot backend (theses-backend)

## Szybki start
1. Wymagania: Java 17+, Maven 3.9+
2. Uruchom:
   ```
   mvn spring-boot:run
   ```
3. API będzie dostępne pod `http://localhost:8080/api`.
4. Konsola H2: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:theses`)

## Główne endpointy
- Theses:
  - GET `/api/theses`
  - POST `/api/theses` body: `{ title, description, promoterId }`
  - DELETE `/api/theses/{id}`
  - PATCH `/api/theses/{id}/student` body: `{ studentId }`
- Schedule tasks:
  - GET `/api/theses/{thesisId}/schedule-tasks`
  - POST `/api/theses/{thesisId}/schedule-tasks` body: `{ name, scope, dueDate }`
  - PATCH `/api/theses/{thesisId}/schedule-tasks/{taskId}` body: partial `{ status|name|scope|dueDate|grade|comments }`
  - DELETE `/api/theses/{thesisId}/schedule-tasks/{taskId}`
- Documents:
  - GET `/api/theses/{thesisId}/documents`
  - POST `/api/theses/{thesisId}/documents` body: `{ type, title, content, order? }`
  - PATCH `/api/theses/{thesisId}/documents/{docId}` body: partial `{ title|content|status|comments|grade }`
  - DELETE `/api/theses/{thesisId}/documents/{docId}`
- Consultations:
  - GET `/api/promoters/{promoterId}/consultations`
  - POST `/api/promoters/{promoterId}/consultations` body: `{ start, end, capacity, notes? }`
  - DELETE `/api/promoters/{promoterId}/consultations/{slotId}`
  - POST `/api/consultations/{slotId}/book` body: `{ studentId }`
  - POST `/api/consultations/{slotId}/cancel` body: `{ studentId }`
- Users:
  - GET `/api/users?role=student|promoter`

## Uwagi
- Projekt używa H2 (in-memory) i `ddl-auto=update` dla szybkiego startu.
- Do produkcji zalecany PostgreSQL i migracje (Flyway/Liquibase) oraz Spring Security + JWT.

