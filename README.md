# Sistema de Autogestion Estudiantil

Aplicacion de consola en Java que simula un sistema de autogestion estudiantil.
Permite registrar materias, gestionar asistencias y calificaciones, conocer la
condicion academica (regular o libre) y obtener reportes de situacion academica
general.

## Instrucciones de ejecucion

1. Abrir el proyecto con **Apache NetBeans IDE 29**
2. Requiere **JDK 25** instalado
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

| Integrante | Rol |
|---|---|
| **Florencia Agustina Zarate** | Interfaz, menus e interaccion con el usuario: implemento las opciones del menu principal (ver perfil, registrar asistencia con advertencias escalonadas, registrar calificacion con validaciones), la busqueda de materias con sobrecarga de metodos, la opcion de volver (0) en todos los puntos del sistema, y se encargo de documentar todo el proceso y dejar apto el entregable final |
| **Cesar Nicolas Chalimond** | Logica academica del sistema: implemento la gestion de materias (inscripcion, baja, listado), los reportes academicos (situacion general, materias en riesgo, aprobadas, ranking), la logica recursiva para promedios y busqueda, la herencia avanzada (MateriaAnual/Cuatrimestral) y la interfaz IRankeable |
| **Alfonso Silva** | Modelado y base estructural del sistema: creo las clases base (PersonaAcademica, Estudiante, Materia, InscripcionMateria), las interfaces (IConsultable, IEvaluable), los constructores, getters/setters con validacion y la herencia entre clases |

## Funcionalidades del sistema

### Menu principal
- **Ver perfil:** muestra los datos del estudiante (nombre, legajo, carrera, anio de ingreso)
- **Gestion de materias:** submenu con inscripcion, baja, listado y busqueda
- **Registrar asistencia:** registra presente/ausente, muestra porcentaje actualizado y emite advertencias si baja del 80% o alerta critica si baja del 75%
- **Registrar calificacion:** agrega notas (rango 0-10, maximo 5 por materia), muestra promedio actualizado e indica si aprobo o no
- **Reportes academicos:** situacion general, materias en riesgo ordenadas por asistencia, materias aprobadas con estadisticas, y ranking por puntaje

### Busqueda de materias (sobrecarga)
- Por nombre: busqueda parcial (ej: "prog" encuentra "Programacion I" y "Programacion II")
- Por cuatrimestre: filtra todas las materias de un cuatrimestre especifico

### Navegacion
- Desde cualquier punto del sistema se puede volver al menu anterior ingresando 0

## Bonus implementados

- **Herencia avanzada:** clases MateriaAnual y MateriaCuatrimestral con porcentajes de regularidad distintos (70% y 75% respectivamente)
- **Recursividad:** calculo del promedio general y busqueda de materia por codigo de forma recursiva
- **Interface IRankeable:** puntaje calculado como (promedio * 0.6) + (asistencia% * 0.4), con ranking ordenado de mayor a menor

## Conceptos de POO aplicados

- **Herencia:** Estudiante extends PersonaAcademica; MateriaAnual/Cuatrimestral extends Materia
- **Polimorfismo:** List<IConsultable> con Estudiante y Materia en reportes
- **Encapsulamiento:** atributos private, acceso por getters/setters con validacion
- **Interfaces:** IConsultable, IEvaluable, IRankeable
- **Clases abstractas:** PersonaAcademica con metodo abstracto mostrarResumen()
- **Sobrecarga:** buscarMateria(String) y buscarMateria(int) en Estudiante
- **Recursividad:** getSumaPromediosRecursivo() y buscarMateriaRecursiva()

## Desafios encontrados

- **Primer proyecto grupal con Git:** al ser el primer proyecto que hacemos en conjunto, no teniamos claro cuando un commit impactaba en el repositorio remoto o si lo hacia. Confundiamos commit (local) con push (remoto), lo que generó confusion al principio hasta que entendimos el flujo completo de trabajo con Git
- **Configuracion de entorno:** incompatibilidades entre IDEs (Visual Studio vs NetBeans) y manejo de archivos ignorados (.vs, nbproject/private) que bloqueaban operaciones de Git
- **Dependencias cruzadas:** cada integrante dependia de metodos implementados por otro, lo que requirio buena comunicacion para detectar y corregir bugs entre las distintas partes (por ejemplo, registrarAsistencia() tenia un error que impedia probar la opcion de asistencia)

## Uso de IA / herramientas externas

Los tres integrantes utilizaron IA como herramienta de asistencia durante el desarrollo. La documentacion individual de cada uno con los prompts utilizados se encuentra en la carpeta `documentacion/`.

| Integrante | Herramientas utilizadas |
|---|---|
| **Florencia Agustina Zarate** | Claude Code (Anthropic) |
| **Cesar Nicolas Chalimond** | Antigravity (IDE) y Gemini 2.5 Pro (agente) |
| **Alfonso Silva** | ChatGPT y Gemini 2.5 Pro |

## Estado del proyecto

Terminado.
