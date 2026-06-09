package sistemaautogestion.zaratesilvachalimond.Modelos;

public class MateriaAnual extends Materia {

    public MateriaAnual() {
        super();
    }

    public MateriaAnual(String nombreP, String codigoP, int anioP) {
        // Al ser anual, podrÃ­amos considerar que abarca ambos cuatrimestres (ej. 0 o ignorarlo)
        // Para respetar la superclase le pasamos 0 o 1 como convenciÃ³n.
        super(nombreP, codigoP, 0, anioP);
    }
    
    // Serialización para el archivo .txt
    @Override
    public String toTexto() {
        return getNombre() + "," + getCodigo() + "," + getAnio();
    }
    public static MateriaAnual fromTexto(String linea) {
        String[] partes = linea.split(",");
        MateriaAnual m = new MateriaAnual(partes[0], partes[1], Integer.parseInt(partes[2]));
        
        return m; // → regresa al DAO con el objeto reconstruido
    }
    
    @Override
    public double getPorcentajeRegularidad() {
        return 70.0;
    }
}

