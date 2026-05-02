
package sistemaautogestion.zaratesilvachalimond;

import java.util.ArrayList;

public class InscripcionMateria implements Evaluable {
    // Atributos/Campos
    private Materia materia;
    private int totalClases;
    private int clasesAsistidas;
    private ArrayList<Double> notas; // Las notas arriba y el perreo abajo.
    
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
    
    // Métodos
    public void registrarAsistencia(boolean presente) {
        for (int i = 0; i < totalClases; i++) { /* ¿Se registran todas las 
                                        asistencias o solo una? AYUDAME LOCO.*/
            if (presente) {
                clasesAsistidas++; /* Si resulta ser de a una por vez, 
                                       solo se usa este if, sin bucle.*/
            }
        }
    }
    
    public void agregarNota(double nota) {
        if (nota >= 0 && nota <= 10 && notas.size() < 5) { //C: alfo te me olvidaste que eran solo 5 x materia, tu no te preocupe papi
            notas.add(nota);
        }
        else {
            System.out.println("Nota invalida o límite de notas (5) alcanzado."); // Te falla?
        }
    }
    
    public double getPorcentajeAsistencia() {
        return (clasesAsistidas * 100) / totalClases;
    }
    
    public @Override String getCondicion() {
        double asistencia = getPorcentajeAsistencia();
        
        if (asistencia >= 75) {
            return "Regular";
        }
        else {
            return "Libre"; // Sos un re hdp diría el profe Edgardo
        }
    }
    
    public @Override double getPromedio() {
        if (notas.isEmpty()) {
            return 0.0;
        }
        
        double suma = 0.0;
        for (Double nota : notas) {
            suma = suma + nota;
        }
        
        return suma / notas.size();
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