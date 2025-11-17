-- 01_create_db.sql

CREATE DATABASE IF NOT EXISTS vetdb
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE vetdb;

-- Tabla Mascota (A)
CREATE TABLE mascota (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    nombre VARCHAR(60) NOT NULL,
    especie VARCHAR(30) NOT NULL,
    raza VARCHAR(60),
    fecha_nacimiento DATE,
    duenio VARCHAR(120) NOT NULL
);

-- Tabla Microchip (B)
CREATE TABLE microchip (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    eliminado BOOLEAN NOT NULL DEFAULT FALSE,
    codigo VARCHAR(25) NOT NULL UNIQUE,
    fecha_implantacion DATE,
    veterinaria VARCHAR(120),
    observaciones VARCHAR(255),
    mascota_id BIGINT NOT NULL UNIQUE,
    CONSTRAINT fk_microchip_mascota
        FOREIGN KEY (mascota_id)
        REFERENCES mascota (id)
        ON DELETE CASCADE
);
