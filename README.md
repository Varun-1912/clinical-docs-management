# 🧾 Clinical Trial Document Management System

A simplified yet practical web application for managing, tracking, and approving **clinical trial documents** — built with **Spring Boot**, **Java 17**, and **MySQL** (or H2 for local testing).

---

## 📘 Overview

During a clinical trial, large volumes of crucial documents are generated — study protocols, consent forms, investigator brochures, and regulatory approvals.  
This system helps research teams and regulatory staff:

- Upload, review, and approve trial-related documents  
- Maintain version control and traceability  
- Ensure only authorized users perform sensitive actions  
- Keep an **audit trail** for compliance

---

## 🏗️ Tech Stack

| Layer | Technology |
|-------|-------------|
| Backend | Java 17, Spring Boot 3+, Spring Web, Spring Data JPA |
| Database | MySQL (or H2 for local setup) |
| Security | Spring Security *(optional)* |
| Build Tool | Maven |
| Frontend (optional) | React / Next.js *(future addition)* |
| Others | Lombok, Validation, Audit Logging |

---

## 🧭 Features

✅ Upload and manage clinical documents (PDF/DOCX)  
✅ Store metadata like title, description, uploader, and upload date  
✅ Track multiple document versions  
✅ Role-based access (Admin / Researcher / Reviewer) *(optional simple version)*  
✅ Maintain an **audit log** for every action (upload, review, approval)  
✅ RESTful APIs for integration with frontend apps  

---

## 📂 Project Structure

clinicaldocs/
├── src/
│ ├── main/
│ │ ├── java/com/example/clinicaldocs/
│ │ │ ├── controller/ # REST endpoints
│ │ │ ├── model/ # Entities (User, Document, AuditLog)
│ │ │ ├── repository/ # JPA Repositories
│ │ │ ├── service/ # Business logic
│ │ │ └── ClinicalDocsApplication.java
│ │ └── resources/
│ │ ├── application.properties # DB + upload path config
│ │ └── static/uploads/ # File uploads stored here
│ └── test/ # Unit tests
├── pom.xml
└── README.md


---

## ⚙️ Prerequisites

Make sure you have the following installed:

- Java 17+  
- Maven 3.9+  
- MySQL (or use H2 for testing)

