
package sistemaautogestion.zaratesilvachalimond;

import java.util.ArrayList;

public class Estudiante extends PersonaAcademica implements Consultable {
    // Atributos/Campos
    private String carrera; // ¿Termino la carrera o la carrera termina conmigo?
    private int anioIngreso;
    private ArrayList<InscripcionMateria> materias;
    
    // Constructor de subclase
    public Estudiante(String nombreP, String legajoP, String carreraP, int anioIngresoP) {
        super(nombreP, legajoP);
        
        this.carrera = carreraP;
        this.anioIngreso = anioIngresoP;
        materias = new ArrayList<>();
    }
    
    // Getters y setters
    public String getCarrera() {return carrera;}
    public int getAnioIngreso() {return anioIngreso;}
    public ArrayList<InscripcionMateria> getMaterias() {return materias;}
    public void setCarrera(String carreraP) {this.carrera = carreraP;}
    public void setAnioIngreso(int anioIngresoP) {this.anioIngreso = anioIngresoP;}
    public void setMaterias(ArrayList<InscripcionMateria> materiasP) {this.materias = materiasP;}
    
    // Métodos
    public void inscribirse(Materia m) {
        InscripcionMateria ins = new InscripcionMateria();
        
        ins.setMateria(m);
        materias.add(ins);
    }
    
    public void darDeBaja(String codigoMateria) { // Me doy de baja de la life.
        for (InscripcionMateria ins : materias) {
            if (codigoMateria.equals(ins.getMateria().getCodigo())) {
                materias.remove(ins);
                break; //Eso necesito ahora mismo.
            }
        }
    }
    
    public InscripcionMateria getInscripcion(String codigoMateria) {
        for (InscripcionMateria ins : materias) {
            if (codigoMateria.equals(ins.getMateria().getCodigo())) {
                return ins;
            }
        }
        return null;
    }
    
    public double getPromedioGeneral() {
        if (materias.isEmpty()) {
            return 0.0;
        }

        double promedio = 0.0;
        
        for (InscripcionMateria ins : materias) {
            promedio += ins.getPromedio();
        }
        
        return promedio / materias.size();
    }
    
    public ArrayList<Materia> getMateriasCriticas() {
        double porcentaje;
        ArrayList<Materia> materiasRiesgo = new ArrayList<>(); 
        
        for (InscripcionMateria ins : materias) {
            porcentaje = ins.getPorcentajeAsistencia();
            
            if (porcentaje >= 75 && porcentaje <= 85) {
                materiasRiesgo.add(ins.getMateria());
            }
        }
        
        return materiasRiesgo;
    }
    
    public @Override void mostrarResumen() {/* No puedo repetir el mismo chiste, 
                                                                         o sí?*/
        System.out.println(getNombre());
        System.out.println(getLegajo());
        System.out.println(carrera);
        System.out.println(anioIngreso);
    }
}