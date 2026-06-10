package sistemaautogestion.zaratesilvachalimond.dao;

import sistemaautogestion.zaratesilvachalimond.Modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.Modelos.Materia;
import java.util.List;

public interface InscripcionDAO {
    boolean registrarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    List<Materia> obtenerMateriasPorEstudiante(String legajoEstudiante) throws Exception;
    boolean actualizarAsistenciaYNotas(InscripcionMateria inscripcion, String legajoEstudiante, String codigoMateria) throws Exception;
    boolean eliminarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    InscripcionMateria obtenerInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    List<InscripcionMateria> obtenerInscripcionesPorEstudiante(String legajoEstudiante) throws Exception;
}
