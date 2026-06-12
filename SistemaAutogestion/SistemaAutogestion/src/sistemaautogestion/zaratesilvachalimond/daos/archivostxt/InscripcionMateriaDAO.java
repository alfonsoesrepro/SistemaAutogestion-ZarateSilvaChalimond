package sistemaautogestion.zaratesilvachalimond.daos.archivostxt;

import sistemaautogestion.zaratesilvachalimond.modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.modelos.Materia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Inscripciones con persistencia en archivo de texto (inscripciones.txt).
 * Implementa la MISMA interfaz que el DAO de MySQL.
 *
 * El archivo guarda SOLO el código de la materia (no una copia de sus datos);
 * los datos de la materia se buscan en materias.txt al cargar. Así, si se edita
 * una materia, el cambio se refleja en las inscripciones (igual que el JOIN en la BD).
 *
 * Formato de cada línea (separador ';'):
 *   legajo;codigoMateria;totalClases;clasesAsistidas;nota1,nota2,...
 * (las notas van separadas por comas; el último campo puede quedar vacío)
 */
public class InscripcionMateriaDAO implements sistemaautogestion.zaratesilvachalimond.daos.bd.InscripcionDAO {

    private static final String ARCHIVO = "inscripciones.txt";

    // Pareja legajo + inscripción (la inscripción por sí sola no sabe de qué alumno es)
    private static class Registro {
        String legajo;
        InscripcionMateria ins;
        Registro(String legajo, InscripcionMateria ins) {
            this.legajo = legajo;
            this.ins = ins;
        }
    }

    // ---------- helpers de archivo ----------
    private ArrayList<Registro> cargarTodos() {
        ArrayList<Registro> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;

        // Cargamos las materias una sola vez para resolver cada inscripción por su código
        List<Materia> materias;
        try {
            materias = new MateriaDAO().obtenerTodas();
        } catch (Exception e) {
            materias = new ArrayList<>();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo, java.nio.charset.StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Registro r = parsear(linea, materias);
                    if (r != null) lista.add(r); // si la materia ya no existe, se ignora
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO + ": " + e.getMessage());
        }
        return lista;
    }

    private void guardarTodos(List<Registro> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, java.nio.charset.StandardCharsets.UTF_8))) {
            for (Registro r : lista) {
                bw.write(serializar(r));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar " + ARCHIVO + ": " + e.getMessage());
        }
    }

    private String serializar(Registro r) {
        StringBuilder notas = new StringBuilder();
        for (int i = 0; i < r.ins.getNotas().size(); i++) {
            notas.append(r.ins.getNotas().get(i));
            if (i < r.ins.getNotas().size() - 1) notas.append(",");
        }
        return r.legajo + ";" + r.ins.getMateria().getCodigo() + ";" +
               r.ins.getTotalClases() + ";" + r.ins.getClasesAsistidas() + ";" + notas;
    }

    // Devuelve null si la materia de esa inscripción ya no existe en materias.txt
    private Registro parsear(String linea, List<Materia> materias) {
        String[] p = linea.split(";", -1); // -1 conserva el campo de notas aunque esté vacío
        String legajo = p[0];
        String codigo = p[1];

        Materia materia = null;
        for (Materia m : materias) {
            if (m.getCodigo().equals(codigo)) {
                materia = m;
                break;
            }
        }
        if (materia == null) return null;

        InscripcionMateria ins = new InscripcionMateria(materia, Integer.parseInt(p[2]), Integer.parseInt(p[3]));
        if (p.length > 4 && !p[4].isEmpty()) {
            ArrayList<Double> notas = new ArrayList<>();
            for (String n : p[4].split(",")) {
                notas.add(Double.parseDouble(n));
            }
            ins.setNotas(notas);
        }
        return new Registro(legajo, ins);
    }

    // ---------- métodos de la interfaz ----------
    @Override
    public boolean registrarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        // La materia tiene que existir
        Materia materia = new MateriaDAO().obtenerPorCodigo(codigoMateria);
        if (materia == null) return false;

        ArrayList<Registro> lista = cargarTodos();
        for (Registro r : lista) {
            if (r.legajo.equals(legajoEstudiante) && r.ins.getMateria().getCodigo().equals(codigoMateria)) {
                return false; // el alumno ya está inscripto en esa materia
            }
        }
        lista.add(new Registro(legajoEstudiante, new InscripcionMateria(materia, 0, 0)));
        guardarTodos(lista);
        return true;
    }

    @Override
    public boolean actualizarAsistenciaYNotas(InscripcionMateria inscripcion, String legajoEstudiante, String codigoMateria) throws Exception {
        ArrayList<Registro> lista = cargarTodos();
        boolean encontrado = false;
        for (Registro r : lista) {
            if (r.legajo.equals(legajoEstudiante) && r.ins.getMateria().getCodigo().equals(codigoMateria)) {
                r.ins = inscripcion; // reemplazamos por la inscripción ya actualizada
                encontrado = true;
                break;
            }
        }
        if (encontrado) guardarTodos(lista);
        return encontrado;
    }

    @Override
    public boolean eliminarInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        ArrayList<Registro> lista = cargarTodos();
        boolean removido = lista.removeIf(r ->
                r.legajo.equals(legajoEstudiante) && r.ins.getMateria().getCodigo().equals(codigoMateria));
        if (removido) guardarTodos(lista);
        return removido;
    }

    @Override
    public InscripcionMateria obtenerInscripcion(String legajoEstudiante, String codigoMateria) throws Exception {
        for (Registro r : cargarTodos()) {
            if (r.legajo.equals(legajoEstudiante) && r.ins.getMateria().getCodigo().equals(codigoMateria)) {
                return r.ins;
            }
        }
        return null;
    }

    @Override
    public List<InscripcionMateria> obtenerInscripcionesPorEstudiante(String legajoEstudiante) throws Exception {
        List<InscripcionMateria> lista = new ArrayList<>();
        for (Registro r : cargarTodos()) {
            if (r.legajo.equals(legajoEstudiante)) {
                lista.add(r.ins);
            }
        }
        return lista;
    }

    @Override
    public void cambiarCodigoMateria(String codigoViejo, String codigoNuevo) throws Exception {
        // Las inscripciones guardan el código de la materia; al cambiarlo, lo actualizamos
        // acá para que no queden "huérfanas" apuntando al código viejo.
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return;

        ArrayList<String> lineas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo, java.nio.charset.StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] p = linea.split(";", -1);
                if (p.length >= 2 && p[1].equals(codigoViejo)) {
                    p[1] = codigoNuevo;
                    linea = String.join(";", p);
                }
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO + ": " + e.getMessage());
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, java.nio.charset.StandardCharsets.UTF_8))) {
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar " + ARCHIVO + ": " + e.getMessage());
        }
    }
}
