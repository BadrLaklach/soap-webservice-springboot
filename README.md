# 🧼 Spring Boot SOAP Web Service — Contract-First

A complete **SOAP Web Service** built with **Spring Boot** using the **Contract-First** approach. The service manages a `Student` entity with full CRUD operations backed by an **H2 in-memory database**.

---

## 🛠️ Tech Stack

| Technology | Role |
|---|---|
| Spring Boot 3.2 | Application framework |
| Spring Web Services | SOAP engine |
| JAXB2 | XSD → Java class generation |
| Spring Data JPA | Data persistence layer |
| H2 | Embedded in-memory database |
| Maven | Build & dependency management |

---

## 📁 Project Structure

```
src/main/
├── java/com/devoir/devoirsoap/
│   ├── DevoirSoapApplication.java   # Spring Boot entry point
│   ├── WebServiceConfig.java        # SOAP servlet + WSDL config
│   ├── EtudiantEndpoint.java        # @Endpoint — 3 SOAP operations
│   ├── EtudiantEntity.java          # JPA entity (H2 table)
│   └── EtudiantRepository.java      # Spring Data JPA repository
└── resources/
    ├── etudiant.xsd                  # XSD contract (Contract-First)
    ├── data.sql                      # Initial seed data
    └── application.properties
```

---

## ⚙️ Contract-First Flow

```
etudiant.xsd  ──(jaxb2-maven-plugin)──▶  Generated Java Classes
                                                   │
                                          EtudiantEndpoint
                                          (@PayloadRoot)
                                                   │
                                          EtudiantRepository
                                          (Spring Data JPA / H2)
```

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+

### Run the application

```bash
mvn generate-sources spring-boot:run
```

The service starts at `http://localhost:8080`.

### WSDL
```
http://localhost:8080/ws/etudiant.wsdl
```

### H2 Console (database browser)
```
http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:etudiantdb
User     : sa
Password : (empty)
```

---

## 🧪 Testing with cURL

**List all students:**
```bash
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://devoir.com/soap/etudiant">
        <soapenv:Body><tns:ListerEtudiantsRequest/></soapenv:Body>
      </soapenv:Envelope>'
```

**Get student by ID:**
```bash
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://devoir.com/soap/etudiant">
        <soapenv:Body><tns:ConsulterEtudiantRequest><tns:id>1</tns:id></tns:ConsulterEtudiantRequest></soapenv:Body>
      </soapenv:Envelope>'
```

**Add a student:**
```bash
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://devoir.com/soap/etudiant">
        <soapenv:Body>
          <tns:AjouterEtudiantRequest>
            <tns:etudiant>
              <tns:id>0</tns:id>
              <tns:nom>Ahmed Soussi</tns:nom>
              <tns:email>ahmed@example.com</tns:email>
            </tns:etudiant>
          </tns:AjouterEtudiantRequest>
        </soapenv:Body>
      </soapenv:Envelope>'
```

---

## 🧪 Testing with SoapUI

1. Open SoapUI → **File → New SOAP Project**
2. Set **Initial WSDL** to `http://localhost:8080/ws/etudiant.wsdl`
3. Click **OK** — all 3 operations are generated automatically
4. Fill in parameters and click ▶ to execute

---

## 📋 SOAP Operations

| Operation | Description |
|---|---|
| `AjouterEtudiant` | Add a new student |
| `ConsulterEtudiant` | Get a student by ID |
| `ListerEtudiants` | Get all students |

---

## 📄 License

MIT
