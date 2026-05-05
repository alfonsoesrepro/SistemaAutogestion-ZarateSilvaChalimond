
package sistemaautogestion.zaratesilvachalimond.modelos;

import sistemaautogestion.zaratesilvachalimond.interfaces.*;


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
        if (codigoP.equals(codigo)) {
            System.out.println("Codigo ya existente.");
        }
        else {
            this.codigo = codigoP;
        }
    }
    public void setCuatrimestre(int cuatrimestreP) {
        if (cuatrimestreP == 1 || cuatrimestreP == 2) {
            this.cuatrimestre = cuatrimestreP;
        }
        else {
            System.out.println("Numero de cuatrimestre invalido.");
        }
    }
    public void setAnio(int anioP) {this.anio = anioP;}
    
    // MÃ©todo implementado de la interface Consultable
    public @Override void mostrarResumen() { // *sonido de látigo*
        System.out.println("Materia: " + nombre + " [" + codigo + "]");
        System.out.println(" -> Dictado: Cuatrimestre " + cuatrimestre + " | Anio: " + anio);
    }
    
    // MÃ©todo para POO Avanzada (Bonus)
    public double getPorcentajeRegularidad() {
        return 75.0;
    }
}
