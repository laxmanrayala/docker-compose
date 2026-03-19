# 🛒 E-Kart Deployment (Docker Compose)

This repository runs the complete E-Kart system using Docker:

- Frontend (React)
- Backend (Spring Boot)
- PostgreSQL (Database)

---

# Create your `.env` file
cp .env.example .env

---

## 🧰 Prerequisites

- Docker
- Docker Compose

---

## 📦 Services

| Service   | Port  |
|----------|-------|
| Frontend | 5173  |
| Backend  | 8080  |
| Database | 5432  |

---

## 🌐 Access URLs

Frontend:
http://localhost:5173

Backend:
http://localhost:8080

---

## 🧠 Architecture

Browser → Frontend → Backend → Database

---

## 🐳 docker-compose.yml

There's file in the directory

---

## ⚠️ Important Notes

- Frontend should call backend using:
  http://localhost:8080(base url)

- `postgres` is used only inside Docker network

---

# Make sure your local PostgreSQL is running

The backend connects to your local Postgres via `host.docker.internal`.
Ensure your database exists and has data before starting.

---

## 🚀 Run Full System

docker compose up -d

---

## 🔄 Restart

docker compose down  
docker compose up -d

---

## 👨‍💻 Author

Laxman Rayala

Result

<img width="852" height="188" alt="Screenshot 2026-03-19 at 12 31 47 PM" src="https://github.com/user-attachments/assets/f071bb76-2606-45c7-a374-711075ea4010" />

<img width="1280" height="646" alt="Screenshot 2026-03-19 at 12 29 47 PM" src="https://github.com/user-attachments/assets/cfa935c3-3ade-4829-9eac-1bf931d63903" />

