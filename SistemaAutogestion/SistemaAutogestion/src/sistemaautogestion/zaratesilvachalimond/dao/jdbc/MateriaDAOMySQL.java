package sistemaautogestion.zaratesilvachalimond.dao.jdbc;

import sistemaautogestion.zaratesilvachalimond.dao.MateriaDAO;
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
}
