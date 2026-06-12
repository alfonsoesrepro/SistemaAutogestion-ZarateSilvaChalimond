# Sistema de Autogestion Estudiantil

Aplicacion de consola en Java que simula un sistema de autogestion estudiantil.
Permite registrar materias, gestionar asistencias y calificaciones, conocer la
condicion academica (regular o libre) y obtener reportes de situacion academica
general.

## Instrucciones de ejecucion

1. Abrir el proyecto con **Apache NetBeans IDE 29**
2. Requiere **JDK 17** instalado
3. Abrir el proyecto desde: `SistemaAutogestion/SistemaAutogestion/`
4. Ejecutar con **F6** o click derecho > Run
5. Si hay problemas de compilacion: **Shift+F11** (Clean and Build)

## Estructura del proyecto

```
SistemaAutogestion/
  src/sistemaautogestion/zaratesilvachalimond/
    SistemaAutogestionZarateSilvaChalimond.java   (Menu principal y menus)
    interfaces/
      IConsultable.java       (Interface - mostrarResumen)
      IEvaluable.java         (Interface - getCondicion, getPromedio, estaAprobada)
      IRankeable.java         (Interface - getPuntajeRanking) [BONUS]
    modelos/
      PersonaAcademica.java   (Clase abstracta base)
      Estudiante.java         (Hereda de PersonaAcademica, implementa IConsultable)
      Materia.java            (Implementa IConsultable)
      MateriaAnual.java       (Hereda de Materia) [BONUS]
      MateriaCuatrimestral.java (Hereda de Materia) [BONUS]
      InscripcionMateria.java (Implementa IEvaluable e IRankeable)
documentacion/
  Documentación_Florencia_Zarate.md
  Documentación_Cesar_Chalimond.md
  Documentación_Alfonso_Silva.md
```

## Integrantes y roles

| Integrante                    | Rol                                                                                                                                                                                                                                                                                                                                                                                        |
| ----------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Florencia Agustina Zarate** | Interfaz, menus e interaccion con el usuario: implemento las opciones del menu principal (ver perfil, registrar asistencia con advertencias escalonadas, registrar calificacion con validaciones), la busqueda de materias con sobrecarga de metodos, la opcion de volver (0) en todos los puntos del sistema, y se encargo de documentar todo el proceso y dejar apto el entregable final |
| **Cesar Nicolas Chalimond**   | Logica academica del sistema: implemento la gestion de materias (inscripcion, baja, listado), los reportes academicos (situacion general, materias en riesgo, aprobadas, ranking), la logica recursiva para promedios y busqueda, la herencia avanzada (MateriaAnual/Cuatrimestral) y la interfaz IRankeable                                                                               |
| **Alfonso Silva**             | Modelado y base estructural del sistema: creo las clases base (PersonaAcademica, Estudiante, Materia, InscripcionMateria), las interfaces (IConsultable, IEvaluable), los constructores, getters/setters con validacion y la herencia entre clases                                                                                                                                         |

## Funcionalidades del sistema

### Menu principal

- **Ver perfil:** muestra los datos del estudiante (nombre, legajo, carrera,
  anio de ingreso)
- **Gestion de materias:** submenu con inscripcion, baja, listado y busqueda
- **Registrar asistencia:** registra presente/ausente, muestra porcentaje
  actualizado y emite advertencias si baja del 80% o alerta critica si baja del
  75%
- **Registrar calificacion:** agrega notas (rango 0-10, maximo 5 por materia),
  muestra promedio actualizado e indica si aprobo o no
- **Reportes academicos:** situacion general, materias en riesgo ordenadas por
  asistencia, materias aprobadas con estadisticas, y ranking por puntaje

### Busqueda de materias (sobrecarga)

- Por nombre: busqueda parcial (ej: "prog" encuentra "Programacion I" y
  "Programacion II")
- Por cuatrimestre: filtra todas las materias de un cuatrimestre especifico

### Navegacion

- Desde cualquier punto del sistema se puede volver al menu anterior ingresando
  0

## Bonus implementados

- **Herencia avanzada:** clases MateriaAnual y MateriaCuatrimestral con
  porcentajes de regularidad distintos (70% y 75% respectivamente)
- **Recursividad:** calculo del promedio general y busqueda de materia por
  codigo de forma recursiva
- **Interface IRankeable:** puntaje calculado como (promedio _ 0.6) +
  (asistencia% _ 0.4), con ranking ordenado de mayor a menor

## Conceptos de POO aplicados

- **Herencia:** Estudiante extends PersonaAcademica; MateriaAnual/Cuatrimestral
  extends Materia
- **Polimorfismo:** List<IConsultable> con Estudiante y Materia en reportes
- **Encapsulamiento:** atributos private, acceso por getters/setters con
  validacion
- **Interfaces:** IConsultable, IEvaluable, IRankeable
- **Clases abstractas:** PersonaAcademica con metodo abstracto mostrarResumen()
- **Sobrecarga:** buscarMateria(String) y buscarMateria(int) en Estudiante
- **Recursividad:** getSumaPromediosRecursivo() y buscarMateriaRecursiva()

## Desafios encontrados

- **Primer proyecto grupal con Git:** al ser el primer proyecto que hacemos en
  conjunto, no teniamos claro cuando un commit impactaba en el repositorio
  remoto o si lo hacia. Confundiamos commit (local) con push (remoto), lo que
  generó confusion al principio hasta que entendimos el flujo completo de
  trabajo con Git
- **Configuracion de entorno:** incompatibilidades entre IDEs (Visual Studio vs
  NetBeans) y manejo de archivos ignorados (.vs, nbproject/private) que
  bloqueaban operaciones de Git
- **Dependencias cruzadas:** cada integrante dependia de metodos implementados
  por otro, lo que requirio buena comunicacion para detectar y corregir bugs
  entre las distintas partes (por ejemplo, registrarAsistencia() tenia un error
  que impedia probar la opcion de asistencia)

## Uso de IA / herramientas externas

Los tres integrantes utilizaron IA como herramienta de asistencia durante el
desarrollo. La documentacion individual de cada uno con los prompts utilizados
se encuentra en la carpeta `documentacion/`.

| Integrante                    | Herramientas utilizadas                     |
| ----------------------------- | ------------------------------------------- |
| **Florencia Agustina Zarate** | Claude Code (Anthropic)                     |
| **Cesar Nicolas Chalimond**   | Antigravity (IDE) y Gemini 2.5 Pro (agente) |
| **Alfonso Silva**             | ChatGPT y Claude (Anthropic)                |

## Estado del proyecto

Terminado.

## Segunda Instancia Evaluativa (IE2): Interfaz Grafica y Persistencia

En esta segunda etapa, el sistema fue migrado de consola a una aplicacion de escritorio
con interfaz grafica, aplicando la arquitectura **MVC + DAO** y persistiendo los datos
en base de datos MySQL.

### Instrucciones de Ejecucion (IE2)

1. Abrir el proyecto con **Apache NetBeans** (version 21 o superior recomendada)
2. Requiere **JDK 17** o superior
3. Las dependencias ya estan incluidas en la carpeta `lib/` del proyecto:
   - `flatlaf-3.7.1.jar`
   - `flatlaf-intellij-themes-3.7.1.jar`
   - `mysql-connector-j-9.7.0.jar`
4. Configurar la base de datos ejecutando `schema.sql` en MySQL (ver seccion de abajo)
5. Ejecutar con **F6** o click derecho > Run Project

### Arquitectura del Proyecto (MVC + DAO)

| Paquete | Responsabilidad |
| :--- | :--- |
| `modelos` | Clases de dominio reutilizadas de IE1 con la logica de negocio intacta |
| `vista` | JFrame principal en Swing. No contiene logica de negocio; solo captura eventos y los delega al controlador |
| `controlador` | Puente orquestador. Recibe acciones de la vista, opera con el modelo y delega la persistencia. Libre de clases Swing |
| `daos/archivostxt` | DAOs de persistencia en archivos `.txt` con serializacion via `fromTexto()` |
| `daos/bd/jdbc` | DAOs de persistencia MySQL via `Connection`, `PreparedStatement` y `ResultSet` |

### Estructura del proyecto (IE2)

```
src/sistemaautogestion/zaratesilvachalimond/
  SistemaAutogestionZarateSilvaChalimond.java  (Main: inicializa conexion y lanza la vista)
  controlador/
    AutogestionController.java
  vista/
    AutogestionView.java                       (JFrame con JTabbedPane, FlatLaf Dracula)
  modelos/
    PersonaAcademica.java
    Estudiante.java
    Materia.java / MateriaAnual.java / MateriaCuatrimestral.java
    InscripcionMateria.java
  interfaces/
    IConsultable.java / IEvaluable.java / IRankeable.java
  daos/
    archivostxt/                               (persistencia en .txt)
    bd/
      EstudianteDAO.java / MateriaDAO.java / InscripcionDAO.java  (interfaces)
      jdbc/
        EstudianteDAOMySQL.java
        MateriaDAOMySQL.java
        InscripcionDAOMySQL.java
```

### Integrantes y Roles (IE2)

| Integrante | Rol en IE2 |
| :--- | :--- |
| **Florencia Agustina Zarate** | **Vista y Diseno UI/UX:** Diseno de la interfaz en Figma (3 prototipos + flow de navegacion con 6 frames), implementacion del JFrame principal en Swing usando el GUI Builder de NetBeans con tema FlatLaf Dracula. Se encargo ademas de la coordinacion del grupo, la estructuracion del proyecto y la documentacion. |
| **Cesar Nicolas Chalimond** | **Controlador y DAOs JDBC:** Desarrollo de `AutogestionController` como capa mediadora entre vista y modelo. Implementacion de los tres DAOs MySQL usando `Connection`, `PreparedStatement` y `ResultSet`. Diseno del esquema de base de datos. |
| **Alfonso Silva** | **Persistencia en archivos de texto y ajustes del modelo:** Implementacion de los DAOs en `archivostxt/` con serializacion via `fromTexto()`, ajuste de constructores y validaciones del modelo, e integracion de las dependencias FlatLaf en el entorno NetBeans del grupo. |

### Diseno UI/UX - Figma

El diseno fue realizado en Figma con 3 iteraciones de prototipo y un flow de navegacion
completo cubriendo todos los frames de la aplicacion.

**Link al diseno:** [Ver prototipo en Figma](https://www.figma.com/design/FVXTMvif1pbKoD8oplDUEr/Untitled?node-id=0-1&t=7I4wTFwIDQyENjFT-1)

Las capturas del diseno se encuentran en la carpeta `documentacion/`.

### Configuracion de Base de Datos

Ejecutar el archivo `schema.sql` incluido en la raiz del proyecto en MySQL antes de
iniciar la aplicacion. Crea las tablas `estudiantes`, `materias` e `inscripciones`
con sus claves foraneas, e inserta datos de prueba con el estudiante legajo `12345`.

### Desafios encontrados (IE2)

- **Migracion de consola a GUI:** Adaptar el flujo lineal de consola al modelo orientado
  a eventos de Swing requirio replantear como interactuan las capas del sistema.
- **Separacion de responsabilidades MVC:** Mantener la vista libre de logica y el
  controlador libre de Swing fue el desafio mas constante, especialmente al sincronizar
  las tablas luego de cada operacion.
- **Dependencias FlatLaf en NetBeans:** Los JARs referenciados desde la PC de un
  integrante no funcionaban en otros entornos. Se resolvio incluyendo los JARs
  directamente en la carpeta `lib/` del proyecto y re-referenciandolos.
- **Sincronizacion de JTable con la BD:** Implementar la recarga automatica de la tabla
  luego de cada alta, baja o edicion sin que los datos queden desincronizados.

### Documentacion de Uso de IA (IE2)

La documentacion individual con los prompts de cada integrante se encuentra en la
carpeta `documentacion/`. Esta entrega es individual y cada archivo indica claramente
de quien es cada prompt.

| Integrante | Herramienta | Archivo |
| :--- | :--- | :--- |
| **Florencia Agustina Zarate** | Claude Code (Anthropic) | `documentacion/Documentacion_Florencia_Zarate.md` |
| **Cesar Nicolas Chalimond** | Antigravity (IDE) + Gemini 2.5 Pro | `documentacion/Documentacion_Cesar_Chalimond.md` |
| **Alfonso Silva** | ChatGPT y Claude (Anthropic) | `documentacion/Documentacion_Alfonso_Silva.md` |