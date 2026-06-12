package sistemaautogestion.zaratesilvachalimond.daos.archivostxt;

import sistemaautogestion.zaratesilvachalimond.modelos.Estudiante;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Estudiantes con persistencia en archivo de texto (estudiantes.txt).
 * Implementa la MISMA interfaz que el DAO de MySQL, así el Controlador, la Vista
 * y el Modelo no necesitan cambiar al pasar de base de datos a archivos.
 */
public class EstudianteDAO implements sistemaautogestion.zaratesilvachalimond.daos.bd.EstudianteDAO {

    private static final String ARCHIVO = "estudiantes.txt";

    // ---------- helpers de archivo ----------
    private ArrayList<Estudiante> cargarTodos() {
        ArrayList<Estudiante> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista; // si no existe, arranca con lista vacía
        try (BufferedReader br = new BufferedReader(new FileReader(archivo, java.nio.charset.StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(Estudiante.fromTexto(linea)); // el Modelo reconstruye el objeto
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO + ": " + e.getMessage());
        }
        return lista;
    }

    private void guardarTodos(List<Estudiante> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, java.nio.charset.StandardCharsets.UTF_8))) {
            for (Estudiante e : lista) {
                bw.write(e.toTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar " + ARCHIVO + ": " + e.getMessage());
        }
    }

    // ---------- métodos de la interfaz ----------
    @Override
    public boolean insertar(Estudiante estudiante) throws Exception {
        ArrayList<Estudiante> lista = cargarTodos();
        for (Estudiante e : lista) {
            if (e.getLegajo().equals(estudiante.getLegajo())) {
                return false; // legajo duplicado
            }
        }
        lista.add(estudiante);
        guardarTodos(lista);
        return true;
    }

    @Override
    public Estudiante obtenerPorLegajo(String legajo) throws Exception {
        for (Estudiante e : cargarTodos()) {
            if (e.getLegajo().equals(legajo)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Estudiante> obtenerTodos() throws Exception {
        return cargarTodos();
    }

    @Override
    public boolean eliminar(String legajo) throws Exception {
        ArrayList<Estudiante> lista = cargarTodos();
        boolean removido = lista.removeIf(e -> e.getLegajo().equals(legajo));
        if (removido) guardarTodos(lista);
        return removido;
    }
}
