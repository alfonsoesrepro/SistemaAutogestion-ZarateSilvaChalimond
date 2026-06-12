
package sistemaautogestion.zaratesilvachalimond.modelos;

import sistemaautogestion.zaratesilvachalimond.interfaces.IConsultable;


public class Materia implements IConsultable{
    // Atributos/Campos
    private String nombre;
    private String codigo;
    private int cuatrimestre;
    private int anio; 
    
    // Constructores (sobrecargas)
    public Materia() {
        this.nombre = "";
        this.codigo = "";
        this.cuatrimestre = 0;
        this.anio = 0;
    }
    public Materia(String nombreP, String codigoP, int cuatrimestreP, int anioP) {
        this.nombre = nombreP;
        this.codigo = codigoP;
        this.cuatrimestre = cuatrimestreP;
        this.anio = anioP;
    }
    
    // Getters y setters
    public String getNombre() {return nombre;}
    public String getCodigo() {return codigo;}
    public int getCuatrimestre() {return cuatrimestre;}
    public int getAnio() {return anio;}
    public void setNombre(String nombreP) {this.nombre = nombreP;}
    public void setCodigo(String codigoP) {
        this.codigo = codigoP;
    }
    public void setCuatrimestre(int cuatrimestreP) {
        if (cuatrimestreP == 1 || cuatrimestreP == 2) {
            this.cuatrimestre = cuatrimestreP;
        }
    }
    public void setAnio(int anioP) {this.anio = anioP;}
    
    // Serialización para el archivo .txt
    public String toTexto() {
        return nombre + "," + codigo + "," + cuatrimestre + "," + anio;
    }
    public static Materia fromTexto(String linea) {
        String[] partes = linea.split(",");
        Materia m = new Materia(partes[0], partes[1], Integer.parseInt(partes[2]), 
                Integer.parseInt(partes[3]));
        
        return m; // → regresa al DAO con el objeto reconstruido
    }

    // Metodo implementado de la interface Consultable
    public @Override String mostrarResumen() {
        return "Materia: " + nombre + " [" + codigo + "]"
             + " -> Dictado: Cuatrimestre " + cuatrimestre + " | Anio: " + anio;
    }
    
    // Metodo para POO Avanzada (Bonus)
    public double getPorcentajeRegularidad() {
        return 75.0;
    }
}
