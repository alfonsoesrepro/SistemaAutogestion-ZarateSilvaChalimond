package sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC;

import sistemaautogestion.zaratesilvachalimond.DAOs.BD.MateriaDAO;
import sistemaautogestion.zaratesilvachalimond.Modelos.Materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MateriaDAOMySQL implements MateriaDAO {
    private Connection conexion;

    public MateriaDAOMySQL(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean insertar(Materia materia) throws Exception {
        String sql = "INSERT INTO materias (codigo, nombre, cuatrimestre) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, materia.getCodigo());
            ps.setString(2, materia.getNombre());
            ps.setInt(3, materia.getCuatrimestre());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Materia obtenerPorCodigo(String codigo) throws Exception {
        String sql = "SELECT * FROM materias WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Materia(rs.getString("nombre"), rs.getString("codigo"), rs.getInt("cuatrimestre"), 1);
                }
            }
        }
        return null;
    }

    @Override
    public List<Materia> obtenerTodas() throws Exception {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM materias";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Materia(rs.getString("nombre"), rs.getString("codigo"), rs.getInt("cuatrimestre"), 1));
            }
        }
        return lista;
    }

    @Override
    public boolean eliminar(String codigo) throws Exception {
        String sql = "DELETE FROM materias WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }
    
    @Override
    public boolean actualizar(Materia materia) throws Exception {
        String sql = "UPDATE materias SET nombre = ?, cuatrimestre = ? WHERE codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getCuatrimestre());
            ps.setString(3, materia.getCodigo());
            return ps.executeUpdate() > 0;
        }
    }
    
    /* La tabla materias en BD no tiene columna anio (solo tiene codigo, nombre, cuatrimestre), 
    y la conexión se inyecta directamente en lugar de usar ConexionBD.getConnection().
    
    obtenerPorCodigo() y obtenerTodas() pasan 1 hardcodeado como anio al construir el objeto Materia, 
    lo que sugiere que anio no existe en la BD. 
    Por eso en el UPDATE tampoco se incluye — si intentaras hacer SET anio = ? 
    fallaría con un error de columna inexistente.
    
    Si en algún momento necesitás persistir el anio, 
    habría que agregar esa columna a la tabla con un ALTER TABLE materias ADD COLUMN anio INT.*/
}
