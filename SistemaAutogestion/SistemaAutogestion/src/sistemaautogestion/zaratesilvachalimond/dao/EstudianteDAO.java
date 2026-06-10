package sistemaautogestion.zaratesilvachalimond.dao;

import sistemaautogestion.zaratesilvachalimond.Modelos.Estudiante;
import java.util.List;

public interface EstudianteDAO {
    boolean insertar(Estudiante estudiante) throws Exception;
    Estudiante obtenerPorLegajo(String legajo) throws Exception;
    List<Estudiante> obtenerTodos() throws Exception;
    boolean eliminar(String legajo) throws Exception;
}
