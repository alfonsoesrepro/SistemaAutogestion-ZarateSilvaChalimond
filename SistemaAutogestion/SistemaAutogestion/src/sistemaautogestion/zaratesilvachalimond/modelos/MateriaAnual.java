package sistemaautogestion.zaratesilvachalimond.modelos;
import sistemaautogestion.zaratesilvachalimond.interfaces.*;

public class MateriaAnual extends Materia {

    public MateriaAnual() {
        super();
    }

    public MateriaAnual(String nombreP, String codigoP, int anioP) {
        // Al ser anual, podrÃ­amos considerar que abarca ambos cuatrimestres (ej. 0 o ignorarlo)
        // Para respetar la superclase le pasamos 0 o 1 como convenciÃ³n.
        super(nombreP, codigoP, 0, anioP);
    }
    
    @Override
    public double getPorcentajeRegularidad() {
        return 70.0;
    }
}

