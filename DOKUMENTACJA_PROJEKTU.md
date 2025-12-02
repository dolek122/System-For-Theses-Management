# Dokumentacja Projektu - System Zarządzania Pracami Dyplomowymi

## 1. Wprowadzenie

### 1.1 Cel projektu
System zarządzania pracami dyplomowymi to aplikacja webowa umożliwiająca kompleksowe zarządzanie procesem realizacji prac dyplomowych. System wspiera współpracę między studentami a promotorami, umożliwiając:
- Rejestrację i przypisywanie prac dyplomowych
- Zarządzanie harmonogramem zadań
- Tworzenie i ocenianie dokumentów
- Rezerwację konsultacji
- Real-time synchronizację zmian

### 1.2 Architektura systemu
Projekt wykorzystuje architekturę **klient-serwer** z podziałem na:
- **Backend**: REST API w Spring Boot (Java)
- **Frontend**: Aplikacja SPA w Angular (TypeScript)
- **Baza danych**: PostgreSQL

---

## 2. Technologie i narzędzia

### 2.1 Backend
- **Java 17** - język programowania
- **Spring Boot 3.3.4** - framework aplikacyjny
- **Spring Data JPA** - warstwa dostępu do danych
- **Hibernate** - ORM (Object-Relational Mapping)
- **PostgreSQL** - relacyjna baza danych
- **Maven** - zarządzanie zależnościami
- **SpringDoc OpenAPI (Swagger)** - dokumentacja API
- **Jakarta Validation** - walidacja danych

### 2.2 Frontend
- **Angular 17+** - framework SPA
- **TypeScript** - język programowania
- **Angular Material** - biblioteka komponentów UI
- **RxJS** - programowanie reaktywne
- **Server-Sent Events (SSE)** - komunikacja real-time

---

## 3. Struktura projektu

### 3.1 Backend (Spring Boot)

```
backend-spring/
├── src/main/java/com/example/theses/
│   ├── config/              # Konfiguracja (CORS, OpenAPI)
│   ├── controller/          # Kontrolery REST API (6 kontrolerów)
│   ├── dto/                 # Data Transfer Objects (10 DTOs)
│   ├── event/               # Eventy dla real-time updates
│   ├── exception/           # Obsługa błędów
│   ├── model/               # Encje JPA (5 encji)
│   ├── repo/                # Repozytoria Spring Data (5 repozytoriów)
│   └── service/             # Logika biznesowa (ThesisEventService)
└── src/main/resources/
    └── application.properties
```

**Główne komponenty:**
- **6 Kontrolerów REST**: Thesis, ScheduleTask, DocumentElement, Consultation, User, ThesisEvents
- **5 Encji JPA**: Thesis, ScheduleTask, DocumentElement, ConsultationSlot, User
- **10 DTOs**: z pełną walidacją danych wejściowych
- **Globalna obsługa błędów**: `@ControllerAdvice` z ProblemDetail
- **Real-time events**: Server-Sent Events dla synchronizacji

### 3.2 Frontend (Angular)

```
theses-management/
├── src/app/
│   ├── features/
│   │   ├── admin/           # Zarządzanie użytkownikami
│   │   ├── auth/            # Logowanie
│   │   ├── promoter/        # Panel promotora (7 komponentów)
│   │   └── student/         # Panel studenta (4 komponenty)
│   ├── guards/              # Role guard (ochrona routingu)
│   ├── models/              # Modele TypeScript
│   └── services/            # Serwisy API (8 serwisów)
```

**Główne komponenty:**
- **Panel Promotora**: Dashboard, Rejestracja prac, Powiązanie studentów, Oceny, Uwagi, Konsultacje, Lista studentów
- **Panel Studenta**: Dashboard, Harmonogram, Dokumenty, Konsultacje
- **Zarządzanie użytkownikami**: Dodawanie/edycja studentów i promotorów

---

## 4. Główne funkcjonalności

### 4.1 Zarządzanie pracami dyplomowymi

**Dla Promotora:**
- ✅ Rejestracja nowych prac dyplomowych (tytuł, opis)
- ✅ Przypisywanie studentów do prac
- ✅ Przeglądanie listy wszystkich prac
- ✅ Usuwanie prac

**Endpointy:**
- `GET /api/theses` - lista wszystkich prac
- `POST /api/theses` - utworzenie nowej pracy
- `DELETE /api/theses/{id}` - usunięcie pracy
- `PATCH /api/theses/{id}/student` - przypisanie studenta

### 4.2 Harmonogram zadań (Schedule Tasks)

**Dla Studenta:**
- ✅ Tworzenie zadań (nazwa, zakres, termin)
- ✅ Zmiana statusu zadania (pending → in_review → completed)
- ✅ Usuwanie zadań

**Dla Promotora:**
- ✅ Przeglądanie zadań studentów
- ✅ Ocenianie zadań (1-5)
- ✅ Dodawanie komentarzy
- ✅ Real-time powiadomienia o zmianach

**Endpointy:**
- `GET /api/theses/{thesisId}/schedule-tasks` - lista zadań
- `POST /api/theses/{thesisId}/schedule-tasks` - utworzenie zadania
- `PATCH /api/theses/{thesisId}/schedule-tasks/{taskId}` - aktualizacja zadania
- `DELETE /api/theses/{thesisId}/schedule-tasks/{taskId}` - usunięcie zadania

**Statusy zadań:**
- `pending` - do zrobienia
- `in_review` - do oceny
- `completed` - zakończone

### 4.3 Dokumenty pracy dyplomowej

**Dla Studenta:**
- ✅ Tworzenie elementów dokumentu (rozdziały, spis treści, bibliografia)
- ✅ Edycja treści dokumentów
- ✅ Zmiana statusu (draft → submitted → reviewed)

**Dla Promotora:**
- ✅ Przeglądanie dokumentów studentów
- ✅ Ocenianie dokumentów (1-5)
- ✅ Dodawanie komentarzy i uwag
- ✅ Real-time powiadomienia o zmianach

**Endpointy:**
- `GET /api/theses/{thesisId}/documents` - lista dokumentów
- `POST /api/theses/{thesisId}/documents` - utworzenie dokumentu
- `PATCH /api/theses/{thesisId}/documents/{docId}` - aktualizacja dokumentu
- `DELETE /api/theses/{thesisId}/documents/{docId}` - usunięcie dokumentu

**Typy dokumentów:**
- `toc` - spis treści
- `chapter` - rozdział
- `bibliography` - bibliografia

### 4.4 System konsultacji

**Dla Promotora:**
- ✅ Tworzenie slotów konsultacyjnych (data, godzina, pojemność)
- ✅ Przeglądanie zapisanych studentów
- ✅ Usuwanie slotów

**Dla Studenta:**
- ✅ Przeglądanie dostępnych konsultacji
- ✅ Rezerwacja konsultacji
- ✅ Anulowanie rezerwacji

**Endpointy:**
- `GET /api/promoters/{promoterId}/consultations` - lista konsultacji
- `POST /api/promoters/{promoterId}/consultations` - utworzenie slotu
- `DELETE /api/promoters/{promoterId}/consultations/{slotId}` - usunięcie slotu
- `POST /api/consultations/{slotId}/book` - rezerwacja
- `POST /api/consultations/{slotId}/cancel` - anulowanie

### 4.5 Zarządzanie użytkownikami

**Funkcjonalności:**
- ✅ Dodawanie nowych użytkowników (studenci, promotorzy)
- ✅ Przeglądanie listy użytkowników
- ✅ Filtrowanie po roli
- ✅ Usuwanie użytkowników
- ✅ Sprawdzanie unikalności email

**Endpointy:**
- `GET /api/users` - lista użytkowników (z filtrem roli)
- `GET /api/users/{id}` - szczegóły użytkownika
- `POST /api/users` - utworzenie użytkownika
- `DELETE /api/users/{id}` - usunięcie użytkownika
- `GET /api/users/students/with-thesis` - studenci z ich pracami i statystykami

### 4.6 Panel statystyk i dashboard

**Dla Promotora:**
- ✅ Statystyki prac dyplomowych
- ✅ Liczba aktywnych studentów
- ✅ Liczba ocen do sprawdzenia
- ✅ Nadchodzące konsultacje
- ✅ Średnie oceny zadań i dokumentów
- ✅ Lista studentów z postępem i statystykami

**Dla Studenta:**
- ✅ Przegląd przypisanej pracy
- ✅ Postęp w zadaniach
- ✅ Status dokumentów
- ✅ Nadchodzące konsultacje

---

## 5. Innowacyjne rozwiązania

### 5.1 Real-time synchronizacja (Server-Sent Events)

**Problem:** Promotor musi odświeżać stronę, aby zobaczyć zmiany wprowadzone przez studenta.

**Rozwiązanie:** Implementacja Server-Sent Events (SSE) dla real-time powiadomień.

**Jak działa:**
1. Promotor subskrybuje strumień zdarzeń dla swoich prac: `GET /api/theses/{thesisId}/events`
2. Gdy student zmienia zadanie lub dokument, backend publikuje event
3. Wszyscy subskrybenci otrzymują powiadomienie w czasie rzeczywistym
4. Frontend automatycznie odświeża dane bez konieczności odświeżania strony

**Zaimplementowane eventy:**
- `schedule_task` - zmiany w zadaniach (created, updated, deleted)
- `document` - zmiany w dokumentach (created, updated, deleted)

**Korzyści:**
- ✅ Natychmiastowa synchronizacja danych
- ✅ Lepsze UX - brak konieczności odświeżania
- ✅ Efektywna komunikacja (SSE jest lżejsze niż WebSocket dla jednokierunkowej komunikacji)

### 5.2 Walidacja danych (DTOs z Bean Validation)

**Problem:** Brak kontroli nad danymi wejściowymi.

**Rozwiązanie:** Wszystkie endpointy przyjmują dane przez DTOs z walidacją.

**Przykłady walidacji:**
- Email: format email, unikalność
- Nazwa: 2-100 znaków
- Opis: 10-2000 znaków
- Status: tylko dozwolone wartości (pending/in_review/completed)
- Ocena: zakres 1-5
- Rola: tylko "student" lub "promoter"

**Korzyści:**
- ✅ Bezpieczeństwo - odrzucanie nieprawidłowych danych
- ✅ Spójne komunikaty błędów
- ✅ Automatyczna walidacja przez Spring

### 5.3 Globalna obsługa błędów

**Problem:** Różne formaty odpowiedzi błędów w różnych endpointach.

**Rozwiązanie:** `@ControllerAdvice` z ProblemDetail (RFC 7807).

**Obsługiwane błędy:**
- `ResourceNotFoundException` - zasób nie znaleziony (404)
- `MethodArgumentNotValidException` - błędy walidacji (400)
- `IllegalArgumentException` - nieprawidłowe argumenty (400)
- `Exception` - ogólne błędy (500)

**Format odpowiedzi:**
```json
{
  "type": "about:blank",
  "title": "Not Found",
  "status": 404,
  "detail": "Thesis with id 123 not found",
  "timestamp": "2025-01-20T10:30:00Z",
  "path": "/api/theses/123"
}
```

### 5.4 Dokumentacja API (Swagger/OpenAPI)

**Funkcjonalność:** Automatyczna dokumentacja API dostępna pod `/swagger-ui.html`

**Zawartość:**
- Opis wszystkich endpointów
- Schematy DTOs
- Przykłady requestów i odpowiedzi
- Możliwość testowania API bezpośrednio z przeglądarki

---

## 6. Model danych

### 6.1 Encje JPA

**User (Użytkownik)**
- id (UUID)
- name (String)
- email (String, unique)
- role (String: "student" | "promoter")

**Thesis (Praca dyplomowa)**
- id (UUID)
- title (String)
- description (String)
- promoterId (String)
- studentId (String, nullable)
- createdAt (Instant)
- updatedAt (Instant)

**ScheduleTask (Zadanie harmonogramu)**
- id (UUID)
- thesisId (String)
- name (String)
- scope (String, max 2000)
- dueDate (String - ISO date)
- status (String: "pending" | "in_review" | "completed")
- grade (Integer, 1-5, nullable)
- comments (String, nullable)

**DocumentElement (Element dokumentu)**
- id (UUID)
- thesisId (String)
- type (String: "toc" | "chapter" | "bibliography")
- title (String)
- content (String, max 10000)
- ordinalValue (Integer, nullable)
- status (String: "draft" | "submitted" | "reviewed")
- comments (String, nullable)
- grade (Integer, 1-5, nullable)
- updatedAt (String - ISO datetime)

**ConsultationSlot (Slot konsultacyjny)**
- id (UUID)
- promoterId (String)
- startTime (String - ISO datetime)
- endTime (String - ISO datetime)
- capacity (Integer)
- registeredStudentIds (List<String>)
- notes (String, nullable)

---

## 7. Bezpieczeństwo i walidacja

### 7.1 Walidacja po stronie backendu
- ✅ Wszystkie DTOs z adnotacjami `@Valid`
- ✅ Sprawdzanie unikalności email
- ✅ Walidacja zakresów (długość tekstu, wartości numeryczne)
- ✅ Walidacja formatów (email, statusy)

### 7.2 CORS Configuration
- ✅ Skonfigurowany CORS dla frontendu (localhost:4200, localhost:3000)
- ✅ Dozwolone metody: GET, POST, PUT, PATCH, DELETE, OPTIONS

### 7.3 Obsługa błędów
- ✅ Spójne formaty odpowiedzi błędów
- ✅ Odpowiednie kody HTTP (400, 404, 409, 500)
- ✅ Szczegółowe komunikaty dla deweloperów

---

## 8. Interfejs użytkownika

### 8.1 Panel Promotora

**Dashboard:**
- Statystyki (prace, studenci, oceny do sprawdzenia)
- Szybkie akcje
- Ostatnie prace
- Nadchodzące konsultacje

**Komponenty:**
1. **Rejestracja prac** - dodawanie nowych prac dyplomowych
2. **Powiązanie studentów** - przypisywanie studentów do prac
3. **Oceny** - ocenianie zadań i dokumentów
4. **Konsultacje** - zarządzanie slotami konsultacyjnymi
5. **Lista studentów** - przegląd studentów z postępem i statystykami
6. **Zarządzanie użytkownikami** - dodawanie studentów i promotorów

### 8.2 Panel Studenta

**Komponenty:**
1. **Dashboard** - przegląd przypisanej pracy
2. **Harmonogram** - zarządzanie zadaniami
3. **Dokumenty** - tworzenie i edycja dokumentów pracy
4. **Konsultacje** - rezerwacja konsultacji

### 8.3 Design
- ✅ Material Design (Angular Material)
- ✅ Responsywny layout
- ✅ Intuicyjna nawigacja
- ✅ Wizualne wskaźniki postępu
- ✅ Kolorowe statusy i badge'e

---

## 9. Instalacja i uruchomienie

### 9.1 Wymagania
- Java 17+
- Maven 3.9+
- PostgreSQL 12+
- Node.js 18+
- npm lub yarn

### 9.2 Backend

```bash
cd backend-spring
# Skonfiguruj application.properties (baza danych)
mvn spring-boot:run
```

API dostępne pod: `http://localhost:8080/api`
Swagger UI: `http://localhost:8080/swagger-ui.html`

### 9.3 Frontend

```bash
cd theses-management
npm install
ng serve
```

Aplikacja dostępna pod: `http://localhost:4200`

### 9.4 Konfiguracja bazy danych

W `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

---

## 10. Podsumowanie

### 10.1 Zaimplementowane funkcjonalności

✅ **Zarządzanie pracami dyplomowymi**
- Rejestracja, przypisywanie studentów, usuwanie

✅ **Harmonogram zadań**
- Tworzenie, edycja, zmiana statusu, ocenianie

✅ **Dokumenty pracy**
- Tworzenie elementów, edycja, ocenianie

✅ **System konsultacji**
- Tworzenie slotów, rezerwacja, anulowanie

✅ **Zarządzanie użytkownikami**
- Dodawanie studentów i promotorów

✅ **Real-time synchronizacja**
- Server-Sent Events dla natychmiastowych aktualizacji

✅ **Walidacja i bezpieczeństwo**
- DTOs z walidacją, globalna obsługa błędów

✅ **Dokumentacja API**
- Swagger/OpenAPI

✅ **Statystyki i raporty**
- Dashboardy z metrykami i postępem

### 10.2 Technologie i wzorce

- **Architektura REST** - standardowe endpointy HTTP
- **DTO Pattern** - separacja warstw
- **Repository Pattern** - abstrakcja dostępu do danych
- **Exception Handling** - centralna obsługa błędów
- **Server-Sent Events** - komunikacja real-time
- **Reactive Programming** - RxJS w Angular
- **Component-Based Architecture** - modularność w Angular

### 10.3 Możliwości rozbudowy

- 🔄 Spring Security + JWT - pełna autentykacja
- 🔄 Migracje bazy danych (Flyway/Liquibase)
- 🔄 Testy jednostkowe i integracyjne
- 🔄 Paginacja i sortowanie
- 🔄 Relacje JPA (@ManyToOne, @OneToMany)
- 🔄 Service Layer - wydzielenie logiki biznesowej
- 🔄 Cache (Redis)
- 🔄 Logowanie operacji
- 🔄 Eksport raportów (PDF)

---

## 11. Wnioski

System zarządzania pracami dyplomowymi to kompleksowa aplikacja webowa wykorzystująca nowoczesne technologie i wzorce projektowe. Główne zalety projektu:

1. **Modularność** - czytelna struktura, łatwa w utrzymaniu
2. **Skalowalność** - możliwość łatwego dodawania nowych funkcji
3. **Real-time** - natychmiastowa synchronizacja danych
4. **Bezpieczeństwo** - walidacja i obsługa błędów
5. **UX** - intuicyjny interfejs użytkownika
6. **Dokumentacja** - automatyczna dokumentacja API

Projekt demonstruje znajomość:
- Frameworków Spring Boot i Angular
- Wzorców projektowych
- REST API design
- Real-time communication
- Database design
- Frontend/Backend integration

---

**Autor:** [Twoje imię i nazwisko]  
**Data:** 2025  
**Wersja:** 1.0

