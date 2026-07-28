# 🍼 Nectivo – Human Milk Bank Management Platform (MVP)

A full-stack Java + React application for managing a human milk bank: donor
registration & screening, milk donation, pasteurization/bottling, inventory
with expiry tracking, and distribution to babies — with JWT-secured REST APIs
and a dashboard with live charts.

This is the **MVP scope** built for a 2-day deadline. It covers 7 core modules
end-to-end (Auth, Donor, Baby, Donation, Inventory, Distribution, Dashboard)
with clean, working, demo-able code — not the full 20-module enterprise plan.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT, Spring Data JPA (Hibernate), MySQL
- **Frontend:** React 18 (Vite), React Router, Axios, Bootstrap 5, Recharts
- **Deployment:** Backend → Render, Frontend → Vercel, Database → any cloud MySQL

## Project Structure
```
Nectivo/
├── backend/     Spring Boot REST API
├── frontend/    React + Vite SPA
├── database/    schema.sql (reference; Hibernate auto-creates tables)
└── README.md
```

---

## 1. Run Locally

### Prerequisites
- JDK 17+, Maven (or use `./mvnw` if you add the wrapper)
- Node.js 18+
- MySQL 8 running locally (or a cloud MySQL instance)

### Backend
```bash
cd backend
# create the database once (or let ddl-auto=update create it for you)
mysql -u root -p -e "CREATE DATABASE nectivo_db"

# set env vars, or just edit application.properties directly
export DB_URL=jdbc:mysql://localhost:3306/nectivo_db
export DB_USERNAME=root
export DB_PASSWORD=your_password
export JWT_SECRET=any-long-random-string
export CORS_ORIGINS=http://localhost:5173

mvn spring-boot:run
```
Backend runs on **http://localhost:8080**

### Frontend
```bash
cd frontend
npm install
cp .env.example .env      # then edit VITE_API_BASE_URL if needed
npm run dev
```
Frontend runs on **http://localhost:5173**

### First-time use
1. Open the frontend → Register a user (pick role `ADMIN` for full access).
2. Add a Donor → click "Clear Screening" (a donation can't be recorded until this is done — mirrors the real workflow).
3. Record a Donation → Approve it → Mark Collected.
4. Go to Inventory → create a Bottle from that donation.
5. Register a Baby.
6. Go to Distribution → issue the bottle to the baby.
7. Check the Dashboard — charts update automatically.

---

## 2. Push to GitHub
```bash
cd Nectivo
git init
git add .
git commit -m "Initial commit: Nectivo MVP - auth, donor, baby, donation, inventory, distribution, dashboard"
git branch -M main
git remote add origin https://github.com/<your-username>/<your-repo>.git
git push -u origin main
```

---

## 3. Deployment

### Database (cloud MySQL — pick one, both have free tiers)
- **Aiven** (https://aiven.io) → free MySQL plan
- **Railway** (https://railway.app) → MySQL plugin
Note down the host, port, database name, username, password once created.

### Backend → Render
1. Push code to GitHub (above).
2. On Render → New → Web Service → connect your repo, root directory `backend`.
3. Environment: **Docker** not required — Render auto-detects Maven/Java, or set:
   - Build Command: `mvn clean package -DskipTests`
   - Start Command: `java -jar target/nectivo-backend-1.0.0.jar`
4. Add environment variables (from your cloud MySQL + a strong secret):
   - `DB_URL=jdbc:mysql://<host>:<port>/<db>?useSSL=true&serverTimezone=UTC`
   - `DB_USERNAME`, `DB_PASSWORD`
   - `JWT_SECRET` (long random string)
   - `CORS_ORIGINS=https://<your-vercel-app>.vercel.app`
5. Deploy. Note the Render URL, e.g. `https://nectivo-backend.onrender.com`.

### Frontend → Vercel
1. On Vercel → New Project → import the same repo, root directory `frontend`.
2. Framework preset: Vite.
3. Environment variable: `VITE_API_BASE_URL=https://nectivo-backend.onrender.com/api`
4. Deploy. Vercel gives you a live URL — that's your demo link for interviews/resume.

---

## 4. API Reference (MVP)

| Method | Endpoint | Description |
|---|---|---|
| POST | /api/auth/register | Register user |
| POST | /api/auth/login | Login, get JWT |
| GET/POST/PUT/DELETE | /api/donors | Donor CRUD |
| PATCH | /api/donors/{id}/clear-screening | Approve donor health screening |
| GET/POST/PUT/DELETE | /api/babies | Baby CRUD |
| GET/POST | /api/donations | Donation records |
| PATCH | /api/donations/{id}/status?status=APPROVED | Approve/Reject/Collect |
| GET/POST | /api/inventory/bottles | Bottle inventory |
| GET | /api/inventory/bottles/available | Available bottles only |
| PATCH | /api/inventory/bottles/{id}/expire | Mark bottle expired |
| GET/POST | /api/distribution | Issue bottle to baby |
| GET | /api/dashboard/stats | Aggregate stats for dashboard |

All endpoints except `/api/auth/**` require header: `Authorization: Bearer <token>`

---

## 5. What's next (beyond MVP, for later)
Hospital & Doctor management, Lab/Quality testing module, Notifications
(email/SMS), QR codes per bottle, role-based dashboards per user type,
audit logs, PDF reports, Swagger docs, Docker, CI/CD, unit tests. The code
is structured (controller/service/repository layers) so these slot in
cleanly without rewriting what's already here.
