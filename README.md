# 🏗️ Bank Locator API (Spring Boot)

---

### 📘 1. **Overview**

**Project Name:** `bank-locator`  
**Purpose:**  
A Spring Boot REST API that accepts a ZIP code and returns nearby banks (within a 10-mile radius) by integrating with the Google Maps Geocoding and Places APIs.

---

### ⚙️ 2. **Technology Stack**

| Layer         | Tech                      |
|---------------|---------------------------|
| Language      | Java 17                   |
| Framework     | Spring Boot 3.x           |
| Build Tool    | Maven 3.9.9               |
| HTTP Client   | `RestTemplate`            |
| JSON Parsing  | org.json                  |
| API Provider  | Google Maps (Geocoding + Places) |
| IDE           | VS Code (on macOS)        |

---

### 🧩 3. **Architecture Style**

- **Type:** Layered Monolith (POC stage)
- **Pattern:** RESTful API
- **Design Principle:** Separation of concerns — each layer handles one responsibility.

```
+-----------------------+
|    Controller Layer   | <---- Accepts request, returns response
+-----------------------+
|    Service Layer      | <---- Business logic (GeoService, BankService)
+-----------------------+
|    Integration Layer  | <---- External API calls (Google Maps)
+-----------------------+
|    DTO Layer          | <---- Data Transfer Objects (ZipCode -> Coordinates -> Banks)
+-----------------------+
```

---

### 📦 4. **Modules & Components**

#### ✅ 1. **Geo Service**
- **Role:** Converts ZIP code → lat/lng using Google Geocoding API
- **Class:** `GeoServiceImpl`
- **Depends on:** `RestTemplate`, Google API

#### ✅ 2. **Bank Service**
- **Role:** Fetches nearby banks using Google Places API
- **Class:** `BankServiceImpl`
- **Depends on:** `RestTemplate`, Google API, `GeoService`

#### ✅ 3. **Controller**
- **Role:** REST endpoint to accept ZIP and return bank list
- **Class:** `BankController`
- **Mapping:** `GET /api/banks?zip=12345`

#### ✅ 4. **DTOs**
- `LocationDto`: Holds latitude & longitude  
- `BankDto`: Holds bank name, address, and distance from user ZIP

---

### 🔗 5. **External APIs**

#### 1. **Google Geocoding API**
- **Purpose:** Convert ZIP → Coordinates
- **Endpoint:**
```
GET https://maps.googleapis.com/maps/api/geocode/json?address={ZIP}&key={API_KEY}
```

#### 2. **Google Places API**
- **Purpose:** Find nearby banks
- **Endpoint:**
```
GET https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={lat,lng}&radius=16093&type=bank&key={API_KEY}
```

---

### 🔄 6. **Data Flow**

1. User hits `GET /api/banks?zip=12345`
2. `GeoService` gets coordinates from ZIP
3. `BankService` fetches banks within 10 miles
4. Returns a list of `BankDto` (name, address, distance)

```
User --> Controller --> GeoService --> Google Geocode
                    --> BankService --> Google Places
                    <-- List<BankDto> (name, address, distance)
```

---

### 🔐 7. **Configuration**

In `application.properties`:
```properties
spring.application.name=bank-locator
google.api.key=YOUR_API_KEY_HERE
```

---

### 🧪 8. **Testing Strategy** *(for future expansion)*

| Layer        | Approach                  |
|--------------|---------------------------|
| Controller   | `@WebMvcTest` + Mock services |
| Service      | Unit tests + Mock external APIs |
| Integration  | Use `TestRestTemplate` or Postman |
| End-to-End   | Manual test with curl/Postman |

---
