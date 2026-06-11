package sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC;

import sistemaautogestion.zaratesilvachalimond.DAOs.BD.InscripcionDAO;
import sistemaautogestion.zaratesilvachalimond.Modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.Modelos.Materia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAOMySQL implements InscripcionDAO {
    private Connection conexion;

    public InscripcionDAOMySQL(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean registrarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        String sql = "INSERT INTO inscripciones (estudiante_id, materia_id, total_clases, clases_asistidas, notas) " +
                     "SELECT e.id, m.id, 0, 0, '' " +
                     "FROM estudiantes e, materias m " +
                     "WHERE e.legajo = ? AND m.codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajoEstudiante);
            ps.setString(2, codigoMateria);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<Materia> obtenerMateriasPorEstudiante(String legajoEstudiante) throws Exception {
        return new ArrayList<>();
    }

    @Override
    public boolean actualizarAsistenciaYNotas(InscripcionMateria inscripcion, String legajoEstudiante, String codigoMateria) throws Exception {
        StringBuilder notasStr = new StringBuilder();
        for (int i = 0; i < inscripcion.getNotas().size(); i++) {
            notasStr.append(inscripcion.getNotas().get(i));
            if (i < inscripcion.getNotas().size() - 1) notasStr.append(",");
        }

        String sql = "UPDATE inscripciones i " +
                     "JOIN estudiantes e ON i.estudiante_id = e.id " +
                     "JOIN materias m ON i.materia_id = m.id " +
                     "SET i.total_clases = ?, i.clases_asistidas = ?, i.notas = ? " +
                     "WHERE e.legajo = ? AND m.codigo = ?";
                     
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, inscripcion.getTotalClases());
            ps.setInt(2, inscripcion.getClasesAsistidas());
            ps.setString(3, notasStr.toString());
            ps.setString(4, legajoEstudiante);
            ps.setString(5, codigoMateria);
            
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean eliminarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        String sql = "DELETE i FROM inscripciones i " +
                     "JOIN estudiantes e ON i.estudiante_id = e.id " +
                     "JOIN materias m ON i.materia_id = m.id " +
                     "WHERE e.legajo = ? AND m.codigo = ?";
                     
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajoEstudiante);
            ps.setString(2, codigoMateria);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public InscripcionMateria obtenerInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        String sql = "SELECT i.total_clases, i.clases_asistidas, i.notas, m.nombre, m.codigo, m.cuatrimestre " + 
                     "FROM inscripciones i " +
                     "JOIN estudiantes e ON i.estudiante_id = e.id " +
                     "JOIN materias m ON i.materia_id = m.id " +
                     "WHERE e.legajo = ? AND m.codigo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajoEstudiante);
            ps.setString(2, codigoMateria);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Materia m = new Materia(rs.getString("nombre"), rs.getString("codigo"), rs.getInt("cuatrimestre"), 1);
                    InscripcionMateria ins = new InscripcionMateria(m, rs.getInt("total_clases"), rs.getInt("clases_asistidas"));
                    
                    String notasStr = rs.getString("notas");
                    if (notasStr != null && !notasStr.isEmpty()) {
                        String[] notasArr = notasStr.split(",");
                        ArrayList<Double> notasList = new ArrayList<>();
                        for (String n : notasArr) {
                            notasList.add(Double.parseDouble(n));
                        }
                        ins.setNotas(notasList);
                    }
                    return ins;
                }
            }
        }
        return null;
    }

    @Override
    public List<InscripcionMateria> obtenerInscripcionesPorEstudiante(String legajoEstudiante) throws Exception {
        List<InscripcionMateria> lista = new ArrayList<>();
        String sql = "SELECT i.total_clases, i.clases_asistidas, i.notas, m.nombre, m.codigo, m.cuatrimestre " + 
                     "FROM inscripciones i " +
                     "JOIN estudiantes e ON i.estudiante_id = e.id " +
                     "JOIN materias m ON i.materia_id = m.id " +
                     "WHERE e.legajo = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, legajoEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Materia m = new Materia(rs.getString("nombre"), rs.getString("codigo"), rs.getInt("cuatrimestre"), 1);
                    InscripcionMateria ins = new InscripcionMateria(m, rs.getInt("total_clases"), rs.getInt("clases_asistidas"));
                    
                    String notasStr = rs.getString("notas");
                    if (notasStr != null && !notasStr.isEmpty()) {
                        String[] notasArr = notasStr.split(",");
                        ArrayList<Double> notasList = new ArrayList<>();
                        for (String n : notasArr) {
                            notasList.add(Double.parseDouble(n));
                        }
                        ins.setNotas(notasList);
                    }
                    lista.add(ins);
                }
            }
        }
        return lista;
    }
}
