CREATE DATABASE IF NOT EXISTS autogestion_estudiantil;
USE autogestion_estudiantil;

CREATE TABLE IF NOT EXISTS estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    legajo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS materias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    cuatrimestre INT NOT NULL
);

CREATE TABLE IF NOT EXISTS inscripciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id INT NOT NULL,
    materia_id INT NOT NULL,
    total_clases INT DEFAULT 0,
    clases_asistidas INT DEFAULT 0,
    notas VARCHAR(255) DEFAULT '',
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (materia_id) REFERENCES materias(id) ON DELETE CASCADE,
    UNIQUE KEY (estudiante_id, materia_id)
);

-- Insertar algunos datos de prueba
INSERT IGNORE INTO estudiantes (legajo, nombre, apellido) VALUES 
('12345', 'Ana', 'García'),
('67890', 'Juan', 'Pérez');

INSERT IGNORE INTO materias (codigo, nombre, cuatrimestre) VALUES 
('MAT01', 'Matemática I', 1),
('PROG1', 'Programación I', 1);
