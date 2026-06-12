-- ============================================================
--  Sistema de Autogestión Estudiantil - Script de Base de Datos
--  Bonus JDBC (MySQL). Ejecutar TODO esto en MySQL Workbench
--  ANTES de abrir la aplicación.
--  Genera la estructura que esperan los DAOs y deja datos de
--  prueba cargados (incluido el alumno con legajo 12345 que
--  muestra la app).
-- ============================================================

CREATE DATABASE IF NOT EXISTS autogestion_estudiantil;
USE autogestion_estudiantil;

-- Borramos para poder re-ejecutar el script desde cero (el orden importa por las FK)
DROP TABLE IF EXISTS inscripciones;
DROP TABLE IF EXISTS materias;
DROP TABLE IF EXISTS estudiantes;

CREATE TABLE estudiantes (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    legajo        VARCHAR(20)  UNIQUE NOT NULL,
    nombre        VARCHAR(100) NOT NULL,
    carrera       VARCHAR(100) NOT NULL,
    anio_ingreso  INT NOT NULL
);

CREATE TABLE materias (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    codigo        VARCHAR(20)  UNIQUE NOT NULL,
    nombre        VARCHAR(100) NOT NULL,
    cuatrimestre  INT NOT NULL,
    anio          INT NOT NULL
);

CREATE TABLE inscripciones (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    estudiante_id    INT NOT NULL,
    materia_id       INT NOT NULL,
    total_clases     INT DEFAULT 0,
    clases_asistidas INT DEFAULT 0,
    notas            VARCHAR(255) DEFAULT '',
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE,
    FOREIGN KEY (materia_id)    REFERENCES materias(id)    ON DELETE CASCADE,
    UNIQUE KEY uk_inscripcion (estudiante_id, materia_id)
);

-- ============================================================
--  DATOS DE PRUEBA
--  El alumno con legajo 12345 es el que muestra la app
--  (está fijo en la Vista), por eso lo dejamos pre-cargado.
-- ============================================================

INSERT INTO estudiantes (legajo, nombre, carrera, anio_ingreso) VALUES
('12345', 'Florencia Agustina', 'Tecnicatura en Programación', 2024);

INSERT INTO materias (codigo, nombre, cuatrimestre, anio) VALUES
('PROG1', 'Programación I',     1, 2024),
('BD1',   'Base de Datos I',    1, 2024),
('MAT2',  'Matemática II',      2, 2024);

-- Inscribimos al alumno 12345 con asistencia y notas variadas, para que
-- la app muestre la tabla llena, los promedios y las ALERTAS (75%-85%):
--   PROG1 -> 90% asistencia (Regular)         | notas 8 y 9
--   BD1   -> 80% asistencia (ALERTA 75-85%)   | nota 7
--   MAT2  -> 75% asistencia (ALERTA 75-85%)   | sin notas
INSERT INTO inscripciones (estudiante_id, materia_id, total_clases, clases_asistidas, notas)
SELECT e.id, m.id, 20, 18, '8,9'
FROM estudiantes e, materias m
WHERE e.legajo = '12345' AND m.codigo = 'PROG1';

INSERT INTO inscripciones (estudiante_id, materia_id, total_clases, clases_asistidas, notas)
SELECT e.id, m.id, 20, 16, '7'
FROM estudiantes e, materias m
WHERE e.legajo = '12345' AND m.codigo = 'BD1';

INSERT INTO inscripciones (estudiante_id, materia_id, total_clases, clases_asistidas, notas)
SELECT e.id, m.id, 20, 15, ''
FROM estudiantes e, materias m
WHERE e.legajo = '12345' AND m.codigo = 'MAT2';

-- Para verificar:
--   SELECT * FROM estudiantes;
--   SELECT * FROM materias;
--   SELECT * FROM inscripciones;
