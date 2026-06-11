package sistemaautogestion.zaratesilvachalimond.Controlador;

import sistemaautogestion.zaratesilvachalimond.Modelos.Estudiante;
import sistemaautogestion.zaratesilvachalimond.Modelos.Materia;
import sistemaautogestion.zaratesilvachalimond.Modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.DAOs.BD.EstudianteDAO;
import sistemaautogestion.zaratesilvachalimond.DAOs.BD.MateriaDAO;
import sistemaautogestion.zaratesilvachalimond.DAOs.BD.InscripcionDAO;

import java.util.List;

public class AutogestionController {

    private final EstudianteDAO estudianteDAO;
    private final MateriaDAO materiaDAO;
    private final InscripcionDAO inscripcionDAO;
    
    private List<Estudiante> estudiantesCache;
    private List<Materia> materiasCache;

    public AutogestionController(EstudianteDAO estudianteDAO, MateriaDAO materiaDAO, InscripcionDAO inscripcionDAO) {
        this.estudianteDAO = estudianteDAO;
        this.materiaDAO = materiaDAO;
        this.inscripcionDAO = inscripcionDAO;
        
        inicializarSistema();
    }

    private void inicializarSistema() {
        try {
            this.estudiantesCache = estudianteDAO.obtenerTodos();
            this.materiasCache = materiaDAO.obtenerTodas();
        } catch (Exception e) {
            throw new RuntimeException("Error crítico en carga inicial desde la Base de Datos: " + e.getMessage());
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
            // Intentar crear la materia (falla si el codigo ya existe)
            try {
                materiaDAO.insertar(new Materia(nombre, codigo, cuatrimestre, anio));
            } catch (Exception ex) {
                // Si la materia ya existe, continuamos e intentamos la inscripción
            }
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

    public List<InscripcionMateria> listarInscripciones(String legajo) {
        try {
            return inscripcionDAO.obtenerInscripcionesPorEstudiante(legajo);
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
}
