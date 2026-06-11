package sistemaautogestion.zaratesilvachalimond.modelos;

public class MateriaCuatrimestral extends Materia {
    
    public MateriaCuatrimestral() {
        super();
    }

    public MateriaCuatrimestral(String nombreP, String codigoP, int cuatrimestreP, int anioP) {
        super(nombreP, codigoP, cuatrimestreP, anioP);
    }
    
    // Serialización para el archivo .txt
    @Override
    public String toTexto() {
        return getNombre() + "," + getCodigo() + "," + getCuatrimestre() + "," + getAnio();
    }
    public static MateriaCuatrimestral fromTexto(String linea) {
        String[] partes = linea.split(",");
        MateriaCuatrimestral m = new MateriaCuatrimestral(partes[0], partes[1], 
                Integer.parseInt(partes[2]), Integer.parseInt(partes[3]));
        
        return m; // → regresa al DAO con el objeto reconstruido
    }
}

