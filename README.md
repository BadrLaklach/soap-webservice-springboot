# Spring Boot SOAP Web Service — Contract-First

A SOAP Web Service built with Spring Boot following the **Contract-First** approach. The service exposes student management operations backed by an H2 in-memory database.

---

## Tech Stack

| Technology | Version | Role |
|---|---|---|
| Spring Boot | 3.2.3 | Application framework |
| Spring Web Services | — | SOAP engine |
| JAXB2 | — | XSD → Java class generation |
| Spring Data JPA | — | Persistence layer |
| H2 | — | Embedded in-memory database |
| Maven | 3.8+ | Build tool |

---

## Project Structure

```
src/main/
├── java/com/devoir/devoirsoap/
│   ├── DevoirSoapApplication.java   # Application entry point
│   ├── WebServiceConfig.java        # SOAP servlet & WSDL configuration
│   ├── EtudiantEndpoint.java        # SOAP operations (@Endpoint)
│   ├── EtudiantEntity.java          # JPA entity
│   └── EtudiantRepository.java      # Spring Data JPA repository
└── resources/
    ├── etudiant.xsd                  # XSD contract (source of truth)
    ├── data.sql                      # Initial seed data
    └── application.properties
```

---

## Architecture

The project follows a strict **Contract-First** design:

1. `etudiant.xsd` defines the data model and all message types
2. The `jaxb2-maven-plugin` generates Java classes from the schema at build time
3. `EtudiantEndpoint` maps incoming SOAP messages to the appropriate handler using `@PayloadRoot`
4. `DefaultWsdl11Definition` auto-generates the WSDL from the XSD at runtime

---

## Getting Started

**Prerequisites:** Java 17+, Maven 3.8+

```bash
mvn generate-sources spring-boot:run
```

| Endpoint | URL |
|---|---|
| SOAP Service | `http://localhost:8080/ws` |
| WSDL | `http://localhost:8080/ws/etudiant.wsdl` |
| H2 Console | `http://localhost:8080/h2-console` |

**H2 Console credentials:**
- JDBC URL: `jdbc:h2:mem:etudiantdb`
- Username: `sa`
- Password: *(leave empty)*

---

## API Reference

| Operation | Description |
|---|---|
| `ListerEtudiants` | Returns all students |
| `ConsulterEtudiant` | Returns a single student by ID |
| `AjouterEtudiant` | Adds a new student |

---

## Testing with cURL

**List all students**
```bash
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://devoir.com/soap/etudiant">
        <soapenv:Body><tns:ListerEtudiantsRequest/></soapenv:Body>
      </soapenv:Envelope>'
```

**Get student by ID**
```bash
curl -s -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tns="http://devoir.com/soap/etudiant">
        <soapenv:Body>
          <tns:ConsulterEtudiantRequest><tns:id>1</tns:id></tns:ConsulterEtudiantRequest>
        </soapenv:Body>
      </soapenv:Envelope>'
```

**Add a student**
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

## Testing with SoapUI

1. Open SoapUI → **File → New SOAP Project**
2. Set **Initial WSDL** to `http://localhost:8080/ws/etudiant.wsdl`
3. Click **OK** — all three operations are generated automatically
4. Select a request, fill in the parameters, and click **Run**

---

## License

MIT
