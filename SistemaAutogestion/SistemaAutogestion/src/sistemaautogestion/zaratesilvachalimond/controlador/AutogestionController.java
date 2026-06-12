package sistemaautogestion.zaratesilvachalimond.controlador;

import sistemaautogestion.zaratesilvachalimond.modelos.Estudiante;
import sistemaautogestion.zaratesilvachalimond.modelos.Materia;
import sistemaautogestion.zaratesilvachalimond.modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.daos.bd.EstudianteDAO;
import sistemaautogestion.zaratesilvachalimond.daos.bd.MateriaDAO;
import sistemaautogestion.zaratesilvachalimond.daos.bd.InscripcionDAO;

import java.util.List;

public class AutogestionController {

    private final EstudianteDAO estudianteDAO;
    private final MateriaDAO materiaDAO;
    private final InscripcionDAO inscripcionDAO;

    public AutogestionController(EstudianteDAO estudianteDAO, MateriaDAO materiaDAO, InscripcionDAO inscripcionDAO) {
        this.estudianteDAO = estudianteDAO;
        this.materiaDAO = materiaDAO;
        this.inscripcionDAO = inscripcionDAO;
        
        inicializarSistema();
    }

    private void inicializarSistema() {
        try {
            // Carga inicial: se leen los datos al arrancar y se valida que la fuente responda.
            estudianteDAO.obtenerTodos();
            materiaDAO.obtenerTodas();
        } catch (Exception e) {
            throw new RuntimeException("Error crítico en carga inicial desde la fuente de datos: " + e.getMessage());
        }
    }

    public Estudiante obtenerEstudiante(String legajo) {
        try {
            return estudianteDAO.obtenerPorLegajo(legajo);
        } catch (Exception e) {
            return null;
        }
    }

    public String registrarAsistencia(String legajo, String codigoMateria, boolean presente) {
        try {
            InscripcionMateria inscripcion = inscripcionDAO.obtenerInscripcion(legajo, codigoMateria);
            if (inscripcion == null) {
                return "Error: Inscripción no encontrada en el sistema.";
            }

            inscripcion.registrarAsistencia(presente);

            boolean exito = inscripcionDAO.actualizarAsistenciaYNotas(inscripcion, legajo, codigoMateria);
            return exito ? "Asistencia registrada correctamente." : "Error: No se pudo guardar la asistencia en la base de datos.";
            
        } catch (Exception e) {
            return "Error interno en la BD: " + e.getMessage();
        }
    }

    public String agregarNotaAInscripcion(String legajo, String codigoMateria, double nota) {
        if (nota < 0 || nota > 10) {
            return "Error: La nota debe estar entre 0 y 10.";
        }
        try {
            InscripcionMateria inscripcion = inscripcionDAO.obtenerInscripcion(legajo, codigoMateria);
            if (inscripcion == null) {
                return "Error: Inscripción no encontrada.";
            }
            if (inscripcion.getNotas().size() >= 5) {
                return "Error: Límite máximo de 5 notas alcanzado.";
            }

            inscripcion.getNotas().add(nota);

            boolean exito = inscripcionDAO.actualizarAsistenciaYNotas(inscripcion, legajo, codigoMateria);
            return exito ? "Nota agregada y guardada correctamente." : "Error: No se pudo guardar la nota en la base de datos.";
            
        } catch (Exception e) {
            return "Error interno en la BD: " + e.getMessage();
        }
    }

    public String darDeBajaMateria(String legajo, String codigoMateria) {
        try {
            boolean exito = inscripcionDAO.eliminarInscripcion(legajo, codigoMateria);
            return exito ? "Se ha dado de baja la inscripción exitosamente." : "Error: La inscripción no existe o no pudo ser eliminada.";
        } catch (Exception e) {
            return "Error interno en la BD: " + e.getMessage();
        }
    }

    public String crearMateriaEInscribir(String legajo, String nombre, String codigo, int cuatrimestre, int anio) {
        if (codigo.length() < 3 || codigo.length() > 10) {
            return "Error: El código debe tener entre 3 y 10 caracteres.";
        }
        if (cuatrimestre != 1 && cuatrimestre != 2) {
            return "Error: El cuatrimestre debe ser 1 o 2.";
        }
        try {
            // El código debe ser ÚNICO: si ya existe una materia con ese código, la rechazamos
            // (antes se ignoraba el error y se inscribía a la materia vieja, mostrando su nombre anterior).
            if (materiaDAO.obtenerPorCodigo(codigo) != null) {
                return "Error: Ya existe una materia con ese código. Elegí otro código.";
            }
            materiaDAO.insertar(new Materia(nombre, codigo, cuatrimestre, anio));
            boolean exito = inscripcionDAO.registrarInscripcion(legajo, codigo);
            return exito ? "Materia registrada e inscripción realizada exitosamente." : "Error: El estudiante ya está inscrito o no existe.";
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("duplicate")) {
                return "Error: código duplicado (materia)";
            }
            return "Error interno en la BD: " + e.getMessage();
        }
    }

    public String inscribirMateria(String legajo, String codigoMateria) {
        try {
            boolean exito = inscripcionDAO.registrarInscripcion(legajo, codigoMateria);
            return exito ? "Inscripción realizada exitosamente." : "Error: El estudiante ya está inscrito o no existe.";
        } catch (Exception e) {
            return "Error interno en la BD: " + e.getMessage();
        }
    }
    
    public String actualizarMateria(String codigoOriginal, String nombre, String codigoNuevo, int cuatrimestre, int anio) {
        if (codigoNuevo == null || codigoNuevo.isBlank()) {
            return "Error: El código de la materia no puede estar vacío.";
        }
        if (codigoNuevo.length() < 3 || codigoNuevo.length() > 10) {
            return "Error: El código debe tener entre 3 y 10 caracteres.";
        }
        if (cuatrimestre != 1 && cuatrimestre != 2) {
            return "Error: El cuatrimestre debe ser 1 o 2.";
        }
        try {
            // Si cambió el código, el nuevo no puede estar usado por otra materia
            if (!codigoNuevo.equals(codigoOriginal) && materiaDAO.obtenerPorCodigo(codigoNuevo) != null) {
                return "Error: Ya existe otra materia con el código " + codigoNuevo + ".";
            }

            Materia actualizada = new Materia(nombre, codigoNuevo, cuatrimestre, anio);
            boolean exito = materiaDAO.actualizar(codigoOriginal, actualizada);
            if (!exito) {
                return "Error: No se encontró la materia con el código " + codigoOriginal + ".";
            }

            // Si cambió el código, propagamos el cambio a las inscripciones (en BD es automático)
            if (!codigoNuevo.equals(codigoOriginal)) {
                inscripcionDAO.cambiarCodigoMateria(codigoOriginal, codigoNuevo);
            }

            return "Materia actualizada correctamente.";
        } catch (Exception e) {
            return "Error interno: " + e.getMessage();
        }
    }

    public List<InscripcionMateria> listarInscripciones(String legajo) {
        try {
            return inscripcionDAO.obtenerInscripcionesPorEstudiante(legajo);
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
}
