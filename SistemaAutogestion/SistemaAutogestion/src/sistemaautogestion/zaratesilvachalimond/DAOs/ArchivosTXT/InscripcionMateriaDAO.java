
package sistemaautogestion.zaratesilvachalimond.DAOs.ArchivosTXT;

import sistemaautogestion.zaratesilvachalimond.Modelos.InscripcionMateria;
import java.io.*;
import java.util.ArrayList;

public class InscripcionMateriaDAO {
    private static final String ARCHIVO = "inscripciones.txt";
    
    public ArrayList<InscripcionMateria> cargarInscripciones() {
        ArrayList<InscripcionMateria> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista; // si no existe, lista vacía

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(InscripcionMateria.fromTexto(linea)); // → llama al Modelo
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return lista; // → regresa al Controlador con la lista completa
    }
    
    public void guardarInscripciones(ArrayList<InscripcionMateria> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (InscripcionMateria ins : lista) {
                bw.write(ins.toTexto()); // → llama al Modelo
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
        // → regresa al Controlador (void, sin resultado)
    }
}