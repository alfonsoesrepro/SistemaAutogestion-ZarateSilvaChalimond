package sistemaautogestion.zaratesilvachalimond.daos.bd;

import sistemaautogestion.zaratesilvachalimond.modelos.InscripcionMateria;
import java.util.List;

public interface InscripcionDAO {
    boolean registrarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    boolean actualizarAsistenciaYNotas(InscripcionMateria inscripcion, String legajoEstudiante, String codigoMateria) throws Exception;
    boolean eliminarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    InscripcionMateria obtenerInscripcion(String legajoEstudiante, String codigoMateria) throws Exception;
    List<InscripcionMateria> obtenerInscripcionesPorEstudiante(String legajoEstudiante) throws Exception;
    void cambiarCodigoMateria(String codigoViejo, String codigoNuevo) throws Exception;
}
