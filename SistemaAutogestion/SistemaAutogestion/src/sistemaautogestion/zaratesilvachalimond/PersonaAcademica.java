package sistemaautogestion.zaratesilvachalimond;


public abstract class PersonaAcademica { //<-- Sí soy 
    // Atributos/Campos
    private String nombre;
    private String legajo;
    
    // Constructor con parámetros
    public PersonaAcademica(String nombreP, String legajoP) {
        this.nombre = nombreP;
        this.legajo = legajoP;
    }
    
    // Getters con validación y setters
    public String getNombre() {
        if (nombre.equals("")) {
            return "Nombre desconocido.";
        }
        else {
            return nombre;
        }
        /* Versión compacta con operador ternario: 
        return (nombre == "") ? "Nombre desconocido" : nombre; 
        
        Igual no me funcionó xd */
    }
    public String getLegajo() {
        if (legajo == null) {
            return "Legajo desconocido.";
        }
        else {
            return legajo;
        }
    }
    public void setNombre(String nombreP) {this.nombre = nombreP;}
    public void setLegajo(String legajoP) {this.legajo = legajoP;}
    
    // Método abstracto
    public abstract void mostrarResumen(); //Así le dice Facu a la IAAAAAAAA
}