
package sistemaautogestion.zaratesilvachalimond.DAOs.ArchivosTXT;

import sistemaautogestion.zaratesilvachalimond.Modelos.Estudiante;
import java.io.*;
import java.util.ArrayList;

public class EstudianteDAO {
    private static final String ARCHIVO = "estudiantes.txt";
    
    public ArrayList<Estudiante> cargarEstudiantes() {
        ArrayList<Estudiante> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) return lista; // si no existe, lista vacía

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(Estudiante.fromTexto(linea)); // → llama al Modelo
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }

        return lista; // → regresa al Controlador con la lista completa
    }
    
    public void guardarEstudiantes(ArrayList<Estudiante> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {
            for (Estudiante e : lista) {
                bw.write(e.toTexto()); // → llama al Modelo
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
        // → regresa al Controlador (void, sin resultado)
    }
}