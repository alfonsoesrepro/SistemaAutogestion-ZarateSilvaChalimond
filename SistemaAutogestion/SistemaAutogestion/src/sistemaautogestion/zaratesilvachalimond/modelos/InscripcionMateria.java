
package sistemaautogestion.zaratesilvachalimond.modelos;

import sistemaautogestion.zaratesilvachalimond.interfaces.*;

import java.util.ArrayList;

public class InscripcionMateria implements IEvaluable, IRankeable {
    // Atributos/Campos
    private Materia materia;
    private int totalClases;
    private int clasesAsistidas;
    private ArrayList<Double> notas; 
    
    // Constructores (sobrecargas)
    public InscripcionMateria() {
        this.materia = new Materia();
        this.totalClases = 0;
        this.clasesAsistidas = 0;
        this.notas = new ArrayList<>();
    }
    public InscripcionMateria(Materia materiaP, int totalClasesP, int clasesAsistidasP) {
        this.materia = materiaP;
        this.totalClases = totalClasesP;
        this.clasesAsistidas = clasesAsistidasP;
        this.notas = new ArrayList<>(); 
    }
    
    // Getters y setters
    public Materia getMateria() {return materia;}
    public int getTotalClases() {return totalClases;}
    public int getClasesAsistidas() {return clasesAsistidas;}
    public ArrayList<Double> getNotas() {return notas;}
    public void setMateria(Materia materiaP) {this.materia = materiaP;} 
    public void setTotalClases(int totalClasesP) {this.totalClases = totalClasesP;}
    public void setClasesAsistidas(int clasesAsistidasP) {this.clasesAsistidas = clasesAsistidasP;}
    public void setNotas(ArrayList<Double> notasP) {this.notas = notasP;}
    
    // MÃ©todos
    public void registrarAsistencia(boolean presente) {
        totalClases++;
        if (presente) clasesAsistidas++;
    }
    
    public void agregarNota(double nota) {
        if (nota >= 0 && nota <= 10 && notas.size() < 5) { 
            notas.add(nota);
        }
        else {
            System.out.println("Nota invalida o lÃ­mite de notas (5) alcanzado."); 
        }
    }
    
    public double getPorcentajeAsistencia() {
        if (this.totalClases <= 0) {
            return 0.0;
        }
        return ((double) this.clasesAsistidas * 100.0) / (double) this.totalClases;
    }
    
    public @Override String getCondicion() {
        double asistencia = getPorcentajeAsistencia();
        
        if (asistencia >= materia.getPorcentajeRegularidad()) {
            return "Regular";
        }
        else {
            return "Libre"; 
        }
    }
    
    @Override
    public double getPuntajeRanking() {
        return (getPromedio() * 0.6) + (getPorcentajeAsistencia() * 0.4);
    }
    
    public @Override double getPromedio() {
        if (notas == null || notas.isEmpty() || notas.size() == 0) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (Double nota : notas) {
            suma = suma + nota;
        }
        
        return suma / (double) notas.size();
    }
    
    public @Override boolean estaAprobada() {
        double promedio = getPromedio();
        String condicion = getCondicion();
        boolean aprobada = false;
        
        if (promedio >= 6 && condicion.equals("Regular")) {
            aprobada = true;
        }
        
        return aprobada;
    }
}
