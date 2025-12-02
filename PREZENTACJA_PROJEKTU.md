# Przewodnik Prezentacji Projektu - System Zarządzania Pracami Dyplomowymi

## 🎯 CEL PROJEKTU (1-2 minuty)

**Problem:** Brak zintegrowanego systemu do zarządzania pracami dyplomowymi, co utrudnia współpracę między studentami a promotorami.

**Rozwiązanie:** Aplikacja webowa umożliwiająca:
- Kompleksowe zarządzanie procesem realizacji prac dyplomowych
- Real-time synchronizację zmian
- System konsultacji i oceniania
- Monitoring postępów studentów

---

## 🏗️ ARCHITEKTURA (2-3 minuty)

### Stack technologiczny:

**Backend:**
- Spring Boot 3.3.4 (Java 17)
- Spring Data JPA + Hibernate
- PostgreSQL
- REST API

**Frontend:**
- Angular 17+ (TypeScript)
- Angular Material
- RxJS (reactive programming)

**Komunikacja:**
- REST API
- Server-Sent Events (real-time)

### Architektura:
```
┌─────────────┐         HTTP/REST         ┌─────────────┐
│   Angular   │ ◄──────────────────────► │ Spring Boot │
│  Frontend   │    Server-Sent Events     │   Backend   │
└─────────────┘                           └─────────────┘
                                                   │
                                                   ▼
                                            ┌─────────────┐
                                            │ PostgreSQL  │
                                            │  Database   │
                                            └─────────────┘
```

---

## 📋 GŁÓWNE FUNKCJONALNOŚCI (5-7 minut)

### 1. Zarządzanie pracami dyplomowymi
- **Promotor:** Rejestracja prac, przypisywanie studentów
- **Student:** Przegląd przypisanej pracy
- **Endpoint:** `POST /api/theses`, `PATCH /api/theses/{id}/student`

### 2. Harmonogram zadań
- **Student:** Tworzenie zadań, zmiana statusu (pending → in_review → completed)
- **Promotor:** Ocenianie zadań (1-5), komentarze
- **Real-time:** Promotor widzi zmiany natychmiast

### 3. Dokumenty pracy
- **Student:** Tworzenie rozdziałów, spisu treści, bibliografii
- **Promotor:** Ocenianie dokumentów, dodawanie uwag
- **Statusy:** draft → submitted → reviewed

### 4. System konsultacji
- **Promotor:** Tworzenie slotów konsultacyjnych (data, godzina, pojemność)
- **Student:** Rezerwacja i anulowanie konsultacji
- **Funkcja:** Kontrola pojemności, lista zapisanych studentów

### 5. Zarządzanie użytkownikami
- Dodawanie studentów i promotorów
- Lista studentów z postępem i statystykami
- Filtrowanie i wyszukiwanie

### 6. Dashboardy i statystyki
- **Promotor:** Statystyki prac, studentów, ocen do sprawdzenia
- **Student:** Postęp w zadaniach, status dokumentów

---

## ⚡ INNOWACYJNE ROZWIĄZANIA (3-4 minuty)

### 1. Real-time synchronizacja (Server-Sent Events)

**Problem:** Promotor musi odświeżać stronę, aby zobaczyć zmiany studenta.

**Rozwiązanie:**
- Student zmienia status zadania → Backend publikuje event
- Promotor otrzymuje powiadomienie w czasie rzeczywistym
- Automatyczne odświeżenie danych bez reload strony

**Demonstracja:** Pokazać zmianę statusu zadania przez studenta i natychmiastowe pojawienie się w panelu promotora.

### 2. Walidacja danych (DTOs + Bean Validation)

**Wszystkie endpointy używają DTOs z walidacją:**
- Email: format + unikalność
- Długość tekstu: min/max znaki
- Statusy: tylko dozwolone wartości
- Oceny: zakres 1-5

**Korzyści:** Bezpieczeństwo, spójne komunikaty błędów

### 3. Globalna obsługa błędów

**`@ControllerAdvice`** - centralna obsługa wszystkich błędów:
- 404 - Resource Not Found
- 400 - Validation Errors
- 409 - Conflict (np. duplikat email)
- 500 - Server Errors

**Format:** RFC 7807 ProblemDetail - standardowy format błędów

### 4. Dokumentacja API (Swagger)

- Automatyczna dokumentacja pod `/swagger-ui.html`
- Możliwość testowania API bezpośrednio z przeglądarki
- Opis wszystkich endpointów, DTOs, przykładów

---

## 🗄️ MODEL DANYCH (2 minuty)

**5 głównych encji:**
1. **User** - użytkownicy (studenci, promotorzy)
2. **Thesis** - prace dyplomowe
3. **ScheduleTask** - zadania harmonogramu
4. **DocumentElement** - elementy dokumentu
5. **ConsultationSlot** - sloty konsultacyjne

**Relacje:**
- Thesis → User (promoterId, studentId)
- ScheduleTask → Thesis
- DocumentElement → Thesis
- ConsultationSlot → User (promoterId)

---

## 📊 STATYSTYKI PROJEKTU (1 minuta)

- **Backend:** 6 kontrolerów, 5 encji, 10 DTOs, 5 repozytoriów
- **Frontend:** 12 komponentów, 8 serwisów
- **Endpointy API:** 20+ endpointów REST
- **Real-time:** Server-Sent Events dla synchronizacji
- **Walidacja:** Pełna walidacja wszystkich danych wejściowych

---

## 🎨 INTERFEJS UŻYTKOWNIKA (2 minuty)

### Panel Promotora:
- Dashboard ze statystykami
- Rejestracja prac
- Powiązanie studentów
- Oceny zadań i dokumentów
- Zarządzanie konsultacjami
- Lista studentów z postępem

### Panel Studenta:
- Dashboard z przypisaną pracą
- Harmonogram zadań
- Dokumenty pracy
- Rezerwacja konsultacji

### Design:
- Material Design (Angular Material)
- Responsywny layout
- Intuicyjna nawigacja
- Wizualne wskaźniki postępu

---

## 🔒 BEZPIECZEŃSTWO I WALIDACJA (1-2 minuty)

- ✅ Walidacja wszystkich danych wejściowych (DTOs)
- ✅ Sprawdzanie unikalności (email)
- ✅ Walidacja zakresów i formatów
- ✅ CORS configuration
- ✅ Globalna obsługa błędów
- ✅ Spójne komunikaty błędów

---

## 🚀 DEMONSTRACJA (5-7 minut)

### Scenariusz 1: Real-time synchronizacja
1. Otwórz panel promotora (oceny)
2. Otwórz panel studenta (harmonogram) w drugim oknie
3. Student zmienia status zadania na "in_review"
4. **POKAŻ:** Zadanie natychmiast pojawia się w panelu promotora

### Scenariusz 2: Pełny workflow
1. Promotor dodaje pracę dyplomową
2. Promotor przypisuje studenta
3. Student tworzy zadanie
4. Student zmienia status na "in_review"
5. Promotor ocenia zadanie
6. Student tworzy dokument
7. Student rezerwuje konsultację

### Scenariusz 3: Walidacja
1. Spróbuj dodać użytkownika z nieprawidłowym emailem
2. **POKAŻ:** Komunikat błędu walidacji
3. Spróbuj dodać użytkownika z istniejącym emailem
4. **POKAŻ:** Błąd 409 Conflict

---

## 📈 MOŻLIWOŚCI ROZBUDOWY (1 minuta)

- Spring Security + JWT (autentykacja)
- Migracje bazy danych (Flyway)
- Testy jednostkowe i integracyjne
- Paginacja i sortowanie
- Service Layer
- Cache (Redis)
- Eksport raportów (PDF)

---

## ✅ PODSUMOWANIE (1-2 minuty)

### Co zostało zaimplementowane:
✅ Kompleksowy system zarządzania pracami dyplomowymi
✅ Real-time synchronizacja zmian
✅ System konsultacji i oceniania
✅ Walidacja i bezpieczeństwo
✅ Dokumentacja API
✅ Intuicyjny interfejs użytkownika

### Technologie:
- Spring Boot (backend)
- Angular (frontend)
- PostgreSQL (baza danych)
- Server-Sent Events (real-time)

### Wzorce projektowe:
- REST API
- DTO Pattern
- Repository Pattern
- Exception Handling
- Component-Based Architecture

---

## ❓ PYTANIA DO PRZYGOTOWANIA

1. **Dlaczego Server-Sent Events zamiast WebSocket?**
   - SSE jest prostsze dla jednokierunkowej komunikacji (server → client)
   - Mniejsze obciążenie, łatwiejsze w implementacji
   - Wystarczające dla tego przypadku użycia

2. **Dlaczego nie ma Spring Security?**
   - Projekt skupia się na funkcjonalności biznesowej
   - Security można łatwo dodać jako rozbudowę
   - Obecnie używany jest role guard w Angular

3. **Jak działa real-time synchronizacja?**
   - Student zmienia dane → Backend publikuje event
   - ThesisEventService wysyła event do wszystkich subskrybentów
   - Frontend automatycznie odświeża dane

4. **Jakie są ograniczenia projektu?**
   - Brak pełnej autentykacji (Spring Security)
   - Brak testów automatycznych
   - Brak paginacji (może być problem przy dużej ilości danych)

5. **Jak można rozbudować projekt?**
   - Dodać Spring Security + JWT
   - Dodać testy jednostkowe i integracyjne
   - Dodać Service Layer
   - Dodać relacje JPA zamiast String ID
   - Dodać paginację i sortowanie

---

## 📝 NOTATKI DLA PREZENTACJI

- **Czas prezentacji:** 15-20 minut + 5 minut na pytania
- **Punkt kulminacyjny:** Demonstracja real-time synchronizacji
- **Najważniejsze:** Pokazać działający system, nie tylko kod
- **Przygotuj:** Dwa okna przeglądarki (student + promotor) do demonstracji

---

**Powodzenia z prezentacją! 🎉**

