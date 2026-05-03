
package sistemaautogestion.zaratesilvachalimond.modelos;

import sistemaautogestion.zaratesilvachalimond.interfaces.*;

import java.util.ArrayList;

public class Estudiante extends PersonaAcademica implements IConsultable {
    // Atributos/Campos
    private String carrera; // Â¿Termino la carrera o la carrera termina conmigo?
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
    
    // MÃ©todos
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
        return getSumaPromediosRecursivo(0) / materias.size();
    }
    
    // MÃ©todo recursivo para sumar promedios (Bonus)
    private double getSumaPromediosRecursivo(int index) {
        if (index >= materias.size()) {
            return 0.0;
        }
        return materias.get(index).getPromedio() + getSumaPromediosRecursivo(index + 1);
    }
    
    // POO: Recursividad - BÃºsqueda de materia por cÃ³digo (Bonus)
    public Materia buscarMateriaRecursiva(String codigoMateria, int index) {
        if (index >= materias.size()) {
            return null;
        }
        if (materias.get(index).getMateria().getCodigo().equals(codigoMateria)) {
            return materias.get(index).getMateria();
        }
        return buscarMateriaRecursiva(codigoMateria, index + 1);
    }

    // POO: Sobrecarga de metodos - buscarMateria por nombre parcial
    public ArrayList<Materia> buscarMateria(String nombreParcial) {
        ArrayList<Materia> resultado = new ArrayList<>();
        String busqueda = nombreParcial.toLowerCase();
        for (InscripcionMateria ins : materias) {
            if (ins.getMateria().getNombre().toLowerCase().contains(busqueda)) {
                resultado.add(ins.getMateria());
            }
        }
        return resultado;
    }

    // POO: Sobrecarga de metodos - buscarMateria por cuatrimestre
    public ArrayList<Materia> buscarMateria(int cuatrimestre) {
        ArrayList<Materia> resultado = new ArrayList<>();
        for (InscripcionMateria ins : materias) {
            if (ins.getMateria().getCuatrimestre() == cuatrimestre) {
                resultado.add(ins.getMateria());
            }
        }
        return resultado;
    }
    
    public ArrayList<Materia> getMateriasCriticas() {
        double porcentaje;
        ArrayList<Materia> materiasRiesgo = new ArrayList<>(); 
        
        for (InscripcionMateria ins : materias) {
            porcentaje = ins.getPorcentajeAsistencia();
            
            if (porcentaje >= 75 && porcentaje < 80) { // Zona de riesgo (menor a 80%)
                materiasRiesgo.add(ins.getMateria());
            }
        }
        
        return materiasRiesgo;
    }
    
    public @Override void mostrarResumen() {/* No puedo repetir el mismo chiste, 
                                                                         o si­?*/
        System.out.println(getNombre());
        System.out.println(getLegajo());
        System.out.println(carrera);
        System.out.println(anioIngreso);
    }
}
