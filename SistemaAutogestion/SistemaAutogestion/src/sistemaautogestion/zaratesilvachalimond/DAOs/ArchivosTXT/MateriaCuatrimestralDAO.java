
package sistemaautogestion.zaratesilvachalimond.DAOs.ArchivosTXT;

import sistemaautogestion.zaratesilvachalimond.Modelos.MateriaCuatrimestral;
import java.io.*;
import java.util.ArrayList;

public class MateriaCuatrimestralDAO {
    private static final String ARCHIVO = "materiasCuatrimestrales.txt";
    
    public ArrayList<MateriaCuatrimestral> cargarMateriasCuatrimestrales() {
        ArrayList<MateriaCuatrimestral> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista; // si no existe, lista vacía

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(MateriaCuatrimestral.fromTexto(linea)); // → llama al Modelo
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return lista; // → regresa al Controlador con la lista completa
    }
    
    public void guardarMateriasCuatrimestrales(ArrayList<MateriaCuatrimestral> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (MateriaCuatrimestral m : lista) {
                bw.write(m.toTexto()); // → llama al Modelo
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
        // → regresa al Controlador (void, sin resultado)
    }
}