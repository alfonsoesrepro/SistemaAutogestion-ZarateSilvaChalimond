
package sistemaautogestion.zaratesilvachalimond.interfaces;


public interface IEvaluable {
    // MÃ©todos
    public String getCondicion();
    public double getPromedio();
    public boolean estaAprobada();
    
    // MÃ©todo default
    public default void mostrarEstadoAcademico() {
        System.out.println(getCondicion());
        System.out.println(getPromedio());
    }
}
