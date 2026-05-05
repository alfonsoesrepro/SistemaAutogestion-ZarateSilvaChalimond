package sistemaautogestion.zaratesilvachalimond.modelos;
import sistemaautogestion.zaratesilvachalimond.interfaces.*;


public abstract class PersonaAcademica { //<-- SÃ­ soy 
    // Atributos/Campos
    private String nombre;
    private String legajo;
    
    // Constructor con parÃ¡metros
    public PersonaAcademica(String nombreP, String legajoP) {
        this.nombre = nombreP;
        this.legajo = legajoP;
    }
    
    // Getters con validaciÃ³n y setters
    public String getNombre() {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Nombre desconocido.";
        }
        else {
            return nombre;
        }
    }
    public String getLegajo() {
        if (legajo == null || legajo.trim().isEmpty()) {
            return "Legajo desconocido.";
        }
        else {
            return legajo;
        }
    }
    public void setNombre(String nombreP) {this.nombre = nombreP;}
    public void setLegajo(String legajoP) {this.legajo = legajoP;}
    
    // MÃ©todo abstracto
    public abstract void mostrarResumen(); 
}
