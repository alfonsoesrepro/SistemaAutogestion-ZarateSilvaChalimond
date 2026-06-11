package sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC;

import sistemaautogestion.zaratesilvachalimond.DAOs.BD.EstudianteDAO;
import sistemaautogestion.zaratesilvachalimond.Modelos.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAOMySQL implements EstudianteDAO {
    private Connection conexion;

    public EstudianteDAOMySQL(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean insertar(Estudiante estudiante) throws Exception {
        String sql = "INSERT INTO estudiantes (legajo, nombre, apellido) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, estudiante.getLegajo());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCarrera()); // Usamos carrera en lugar de apellido
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public Estudiante obtenerPorLegajo(String legajo) throws Exception {
        String sql = "SELECT * FROM estudiantes WHERE legajo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estudiante(rs.getString("nombre"), rs.getString("legajo"), rs.getString("apellido"), 2023);
                }
            }
        }
        return null;
    }

    @Override
    public List<Estudiante> obtenerTodos() throws Exception {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estudiante(rs.getString("nombre"), rs.getString("legajo"), rs.getString("apellido"), 2023));
            }
        }
        return lista;
    }

    @Override
    public boolean eliminar(String legajo) throws Exception {
        String sql = "DELETE FROM estudiantes WHERE legajo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajo);
            return ps.executeUpdate() > 0;
        }
    }
}
