package sistemaautogestion.zaratesilvachalimond.daos.archivostxt;

import sistemaautogestion.zaratesilvachalimond.modelos.Materia;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO de Materias con persistencia en archivo de texto (materias.txt).
 * Implementa la MISMA interfaz que el DAO de MySQL.
 */
public class MateriaDAO implements sistemaautogestion.zaratesilvachalimond.daos.bd.MateriaDAO {

    private static final String ARCHIVO = "materias.txt";

    // ---------- helpers de archivo ----------
    private ArrayList<Materia> cargarTodas() {
        ArrayList<Materia> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo, java.nio.charset.StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(Materia.fromTexto(linea));
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer " + ARCHIVO + ": " + e.getMessage());
        }
        return lista;
    }

    private void guardarTodas(List<Materia> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, java.nio.charset.StandardCharsets.UTF_8))) {
            for (Materia m : lista) {
                bw.write(m.toTexto());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar " + ARCHIVO + ": " + e.getMessage());
        }
    }

    // ---------- métodos de la interfaz ----------
    @Override
    public boolean insertar(Materia materia) throws Exception {
        ArrayList<Materia> lista = cargarTodas();
        for (Materia m : lista) {
            if (m.getCodigo().equals(materia.getCodigo())) {
                return false; // código duplicado
            }
        }
        lista.add(materia);
        guardarTodas(lista);
        return true;
    }

    @Override
    public Materia obtenerPorCodigo(String codigo) throws Exception {
        for (Materia m : cargarTodas()) {
            if (m.getCodigo().equals(codigo)) {
                return m;
            }
        }
        return null;
    }

    @Override
    public List<Materia> obtenerTodas() throws Exception {
        return cargarTodas();
    }

    @Override
    public boolean eliminar(String codigo) throws Exception {
        ArrayList<Materia> lista = cargarTodas();
        boolean removido = lista.removeIf(m -> m.getCodigo().equals(codigo));
        if (removido) guardarTodas(lista);
        return removido;
    }

    @Override
    public boolean actualizar(String codigoOriginal, Materia materia) throws Exception {
        ArrayList<Materia> lista = cargarTodas();
        boolean encontrada = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo().equals(codigoOriginal)) {
                lista.set(i, materia); // reemplaza (puede traer un código nuevo)
                encontrada = true;
                break;
            }
        }
        if (encontrada) guardarTodas(lista);
        return encontrada;
    }
}
