package sistemaautogestion.zaratesilvachalimond;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class SistemaAutogestionZarateSilvaChalimond {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); 
        Estudiante alumno = new Estudiante("Ana Garcia", "22001", "Interfaz Grafica", 2023); 
        
        int opcion = -1;
        do { 
            System.out.println("\n=== MENU PRINCIPAL ==="); 
            System.out.println("1. Ver perfil"); 
            System.out.println("2. Gestion de materias"); 
            System.out.println("3. Registrar asistencia"); 
            System.out.println("4. Registrar calificacion"); 
            System.out.println("5. Ver reportes"); 
            System.out.println("0. Salir");     
            System.out.print("Opcion: ");     
            
            try {
                opcion = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {         
                case 1:
                    // TODO (Agustina): Ver perfil del estudiante (Llamar a mostrarResumen)
                    System.out.println(">> TODO: Agustina implementar esto <<");
                    break;
                case 2:
                    menuMaterias(alumno, sc);         
                    break;
                case 3:
                    // TODO (Agustina): Registrar asistencia pidiendo codigo y presente/ausente
                    System.out.println(">> TODO: Agustina implementar esto <<");
                    break;
                case 4:
                    // TODO (Agustina): Registrar calificacion pidiendo codigo y nota
                    System.out.println(">> TODO: Agustina implementar esto <<");
                    break;
                case 5:
                    menuReportes(alumno);
                    break;
                case 0:
                    System.out.println("Hasta luego!");         
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");     
            } 
        } while (opcion != 0); 
    }
    
    // ===== ZONA NAO: GESTION DE MATERIAS Y REPORTES =====
    
    private static void menuMaterias(Estudiante alumno, Scanner sc) {
        int opc = -1;
        do {
            System.out.println("\n--- GESTION DE MATERIAS ---");
            System.out.println("1. Inscribirse a una materia");
            System.out.println("2. Darse de baja de una materia");
            System.out.println("3. Listar materias inscriptas");
            System.out.println("4. Buscar materia");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            try {
                opc = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                opc = -1;
            }
            
            switch(opc) {
                case 1:
                    inscribirMateria(alumno, sc);
                    break;
                case 2:
                    darDeBajaMateria(alumno, sc);
                    break;
                case 3:
                    listarMaterias(alumno);
                    break;
                case 4:
                    // TODO (Agustina): Implementar llamada a buscarMateria usando sobrecarga
                    System.out.println(">> TODO: Agustina implementar esto <<");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while(opc != 0);
    }
    
    private static void inscribirMateria(Estudiante alumno, Scanner sc) {
        System.out.println("\n--- INSCRIPCION ---");
        System.out.print("Nombre de la materia: ");
        String nombre = sc.nextLine();
        
        System.out.print("Codigo de la materia (3 a 10 caracteres): ");
        String codigo = sc.nextLine();
        
        if (codigo.length() < 3 || codigo.length() > 10) {
            System.out.println("Error: El codigo debe tener entre 3 y 10 caracteres.");
            return;
        }
        
        // Validar duplicado usando la recursividad recien implementada
        if (alumno.buscarMateriaRecursiva(codigo, 0) != null) {
            System.out.println("Error: Ya estas inscripto en una materia con ese codigo.");
            return;
        }
        
        System.out.print("Cuatrimestre (1 o 2): ");
        int cuatri = 0;
        try { cuatri = Integer.parseInt(sc.nextLine()); } catch(Exception e) {}
        
        if (cuatri != 1 && cuatri != 2) {
            System.out.println("Error: Cuatrimestre invalido.");
            return;
        }
        
        System.out.print("Anio: ");
        int anio = 0;
        try { anio = Integer.parseInt(sc.nextLine()); } catch(Exception e) {}
        
        System.out.print("Es una materia anual? (s/n): ");
        String esAnual = sc.nextLine();
        Materia nuevaMateria;
        
        // BONUS: Instanciar Cuatrimestral o Anual (Polimorfismo)
        if (esAnual.equalsIgnoreCase("s")) {
            nuevaMateria = new MateriaAnual(nombre, codigo, anio);
        } else {
            nuevaMateria = new MateriaCuatrimestral(nombre, codigo, cuatri, anio);
        }
        
        alumno.inscribirse(nuevaMateria);
        System.out.println("Inscripcion exitosa.");
    }
    
    private static void darDeBajaMateria(Estudiante alumno, Scanner sc) {
        System.out.print("Ingrese el codigo de la materia a dar de baja: ");
        String codigo = sc.nextLine();
        if (alumno.buscarMateriaRecursiva(codigo, 0) != null) {
            alumno.darDeBaja(codigo);
            System.out.println("Materia dada de baja correctamente.");
        } else {
            System.out.println("No se encontro inscripcion con ese codigo.");
        }
    }
    
    private static void listarMaterias(Estudiante alumno) {
        System.out.println("\n--- MATERIAS INSCRIPTAS ---");
        if (alumno.getMaterias().isEmpty()) {
            System.out.println("No hay materias inscriptas.");
            return;
        }
        for (InscripcionMateria ins : alumno.getMaterias()) {
            System.out.println("- " + ins.getMateria().getNombre() + " (" + ins.getMateria().getCodigo() + ") | Condicion: " + ins.getCondicion() + " | Promedio: " + ins.getPromedio());
        }
    }
    
    private static void menuReportes(Estudiante alumno) {
        System.out.println("\n--- REPORTES ACADEMICOS ---");
        reporteGeneral(alumno);
        reporteRiesgo(alumno);
        reporteAprobadas(alumno);
        reporteRanking(alumno);
    }
    
    private static void reporteGeneral(Estudiante alumno) {
        System.out.println("\n1. Reporte Situacion General (Demostracion de Polimorfismo)");
        List<Consultable> consultables = new ArrayList<>();
        consultables.add(alumno); // El estudiante implementa Consultable
        for (InscripcionMateria ins : alumno.getMaterias()) {
            consultables.add(ins.getMateria()); // Materia implementa Consultable
        }
        
        System.out.println("Iterando Consultables...");
        for (Consultable c : consultables) {
            System.out.println("-----------------");
            c.mostrarResumen();
        }
        System.out.println("-----------------");
        
        int regulares = 0, libres = 0;
        for (InscripcionMateria ins : alumno.getMaterias()) {
            if (ins.getCondicion().equals("Regular")) regulares++;
            else libres++;
        }
        System.out.println("Promedio general del estudiante: " + alumno.getPromedioGeneral());
        System.out.println("Materias Regulares: " + regulares + " | Libres: " + libres + " | En Riesgo: " + alumno.getMateriasCriticas().size());
    }
    
    private static void reporteRiesgo(Estudiante alumno) {
        System.out.println("\n2. Reporte Materias en Riesgo (Asistencia 75% - 85%)");
        ArrayList<Materia> criticas = alumno.getMateriasCriticas();
        if (criticas.isEmpty()) {
            System.out.println("No hay materias en riesgo.");
            return;
        }
        
        ArrayList<InscripcionMateria> inscripcionesCriticas = new ArrayList<>();
        for (Materia m : criticas) {
            inscripcionesCriticas.add(alumno.getInscripcion(m.getCodigo()));
        }
        
        // Ordenamiento ascendente por asistencia
        inscripcionesCriticas.sort(new Comparator<InscripcionMateria>() {
            public int compare(InscripcionMateria i1, InscripcionMateria i2) {
                return Double.compare(i1.getPorcentajeAsistencia(), i2.getPorcentajeAsistencia());
            }
        });
        
        for (InscripcionMateria ins : inscripcionesCriticas) {
            System.out.println("- " + ins.getMateria().getNombre() + " | Asistencia: " + ins.getPorcentajeAsistencia() + "%");
        }
    }
    
    private static void reporteAprobadas(Estudiante alumno) {
        System.out.println("\n3. Reporte Materias Aprobadas");
        ArrayList<Double> todasLasNotasAprobadas = new ArrayList<>();
        
        for (InscripcionMateria ins : alumno.getMaterias()) {
            if (ins.estaAprobada()) {
                System.out.println("- " + ins.getMateria().getNombre() + " (Promedio: " + ins.getPromedio() + ")");
                todasLasNotasAprobadas.addAll(ins.getNotas());
            }
        }
        
        if (!todasLasNotasAprobadas.isEmpty()) {
            double max = Collections.max(todasLasNotasAprobadas);
            double min = Collections.min(todasLasNotasAprobadas);
            double suma = 0;
            for (double n : todasLasNotasAprobadas) suma += n;
            System.out.println("Estadisticas de notas aprobadas -> Max: " + max + " | Min: " + min + " | Promedio: " + (suma/todasLasNotasAprobadas.size()));
        } else {
            System.out.println("No hay materias aprobadas.");
        }
    }
    
    private static void reporteRanking(Estudiante alumno) {
        System.out.println("\n4. Ranking de Materias (BONUS Interface Rankeable)");
        if (alumno.getMaterias().isEmpty()) {
            System.out.println("No hay materias inscriptas.");
            return;
        }
        
        ArrayList<InscripcionMateria> ranking = new ArrayList<>(alumno.getMaterias());
        ranking.sort(new Comparator<InscripcionMateria>() {
            public int compare(InscripcionMateria i1, InscripcionMateria i2) {
                return Double.compare(i2.getPuntajeRanking(), i1.getPuntajeRanking()); // Orden Descendente
            }
        });
        
        for (int i = 0; i < ranking.size(); i++) {
            System.out.println((i+1) + " Puesto - " + ranking.get(i).getMateria().getNombre() + " | Puntaje: " + ranking.get(i).getPuntajeRanking());
        }
    }
}
