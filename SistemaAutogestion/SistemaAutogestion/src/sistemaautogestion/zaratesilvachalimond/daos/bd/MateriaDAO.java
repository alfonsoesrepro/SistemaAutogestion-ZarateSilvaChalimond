package sistemaautogestion.zaratesilvachalimond.daos.bd;

import sistemaautogestion.zaratesilvachalimond.modelos.Materia;
import java.util.List;

public interface MateriaDAO {
    boolean insertar(Materia materia) throws Exception;
    Materia obtenerPorCodigo(String codigo) throws Exception;
    List<Materia> obtenerTodas() throws Exception;
    boolean eliminar(String codigo) throws Exception;
    boolean actualizar(String codigoOriginal, Materia materia) throws Exception;
}
