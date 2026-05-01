
package sistemaautogestion.zaratesilvachalimond;


public interface Evaluable {
    // Métodos
    public String getCondicion();
    public double getPromedio();
    public boolean estaAprobada();
    
    // Método default
    public default void mostrarEstadoAcademico() {
        System.out.println(getCondicion());
        System.out.println(getPromedio());
    }
}