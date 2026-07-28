-- Nectivo Database Schema
-- This is auto-created by Hibernate (ddl-auto=update) when the backend starts,
-- but you can also run this manually if you prefer to design it yourself first.

CREATE DATABASE IF NOT EXISTS nectivo_db;
USE nectivo_db;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    role VARCHAR(20) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS donors (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,
    full_name VARCHAR(150) NOT NULL,
    blood_group VARCHAR(10),
    date_of_birth DATE,
    address VARCHAR(255),
    health_screening_cleared BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS babies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    baby_name VARCHAR(150) NOT NULL,
    date_of_birth DATE,
    birth_weight_kg DOUBLE,
    parent_name VARCHAR(150) NOT NULL,
    parent_contact VARCHAR(20) NOT NULL,
    nicu_admitted BOOLEAN DEFAULT FALSE,
    diagnosis_notes VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS donations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    donor_id BIGINT NOT NULL,
    quantity_ml DOUBLE NOT NULL,
    donation_date DATETIME,
    status VARCHAR(20) DEFAULT 'PENDING',
    notes VARCHAR(500),
    FOREIGN KEY (donor_id) REFERENCES donors(id)
);

CREATE TABLE IF NOT EXISTS milk_bottles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bottle_code VARCHAR(50) NOT NULL UNIQUE,
    donation_id BIGINT NOT NULL,
    quantity_ml DOUBLE NOT NULL,
    storage_location VARCHAR(100),
    expiry_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'AVAILABLE',
    FOREIGN KEY (donation_id) REFERENCES donations(id)
);

CREATE TABLE IF NOT EXISTS distributions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bottle_id BIGINT NOT NULL,
    baby_id BIGINT NOT NULL,
    approved_by_doctor VARCHAR(150) NOT NULL,
    distributed_at DATETIME,
    remarks VARCHAR(500),
    FOREIGN KEY (bottle_id) REFERENCES milk_bottles(id),
    FOREIGN KEY (baby_id) REFERENCES babies(id)
);
