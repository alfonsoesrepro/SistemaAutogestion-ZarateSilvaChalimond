package sistemaautogestion.zaratesilvachalimond;

import sistemaautogestion.zaratesilvachalimond.Interfaces.IConsultable;
import sistemaautogestion.zaratesilvachalimond.Modelos.Materia;
import sistemaautogestion.zaratesilvachalimond.Modelos.Estudiante;
import sistemaautogestion.zaratesilvachalimond.Modelos.InscripcionMateria;
import sistemaautogestion.zaratesilvachalimond.Modelos.MateriaCuatrimestral;
import sistemaautogestion.zaratesilvachalimond.Modelos.MateriaAnual;
import sistemaautogestion.zaratesilvachalimond.Vista.AutogestionView;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.UIManager;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class SistemaAutogestionZarateSilvaChalimond {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDraculaIJTheme());

            Font interRegular = Font.createFont(
                    Font.TRUETYPE_FONT,
                    SistemaAutogestionZarateSilvaChalimond.class.getResourceAsStream("/resources/Inter-Regular.otf")
            ).deriveFont(13f);

            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(interRegular);
            UIManager.put("defaultFont", interRegular);

        } catch (Exception ex) {
            System.out.println("No se pudo cargar FlatLaf o Inter: " + ex.getMessage());
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    java.sql.Connection conexion = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/autogestion_estudiantil", "root", "");
                    sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.EstudianteDAOMySQL estudianteDAO = new sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.EstudianteDAOMySQL(conexion);
                    sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.MateriaDAOMySQL materiaDAO = new sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.MateriaDAOMySQL(conexion);
                    sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.InscripcionDAOMySQL inscripcionDAO = new sistemaautogestion.zaratesilvachalimond.DAOs.BD.JDBC.InscripcionDAOMySQL(conexion);
                    
                    sistemaautogestion.zaratesilvachalimond.Controlador.AutogestionController controlador = new sistemaautogestion.zaratesilvachalimond.Controlador.AutogestionController(estudianteDAO, materiaDAO, inscripcionDAO);
                    
                    new AutogestionView(controlador).setVisible(true);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error crítico conectando a la BD:\n" + e.getMessage());
                    System.exit(1);
                }
            }
        });
    }
    // ===== ZONA CESAR: GESTION DE MATERIAS Y REPORTES =====

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

            switch (opc) {
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
                    System.out.println("\n--- BUSCAR MATERIA ---");
                    System.out.println("1. Por nombre (busqueda parcial)");
                    System.out.println("2. Por cuatrimestre");
                    System.out.println("0. Volver");
                    System.out.print("Opcion: ");
                    int tipoBusqueda;
                    try {
                        tipoBusqueda = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Opcion invalida.");
                        break;
                    }

                    if (tipoBusqueda == 0) {
                        break;
                    }

                    ArrayList<Materia> encontradas = new ArrayList<>();
                    if (tipoBusqueda == 1) {
                        System.out.print("Ingrese parte del nombre: ");
                        String nombreBuscado = sc.nextLine();
                        encontradas = alumno.buscarMateria(nombreBuscado);
                    } else if (tipoBusqueda == 2) {
                        System.out.print("Ingrese cuatrimestre (1 o 2): ");
                        int cuatriBuscado;
                        try {
                            cuatriBuscado = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Cuatrimestre invalido.");
                            break;
                        }
                        encontradas = alumno.buscarMateria(cuatriBuscado);
                    } else {
                        System.out.println("Opcion invalida.");
                        break;
                    }

                    if (encontradas.isEmpty()) {
                        System.out.println("No se encontraron materias.");
                    } else {
                        System.out.println("Materias encontradas:");
                        for (Materia m : encontradas) {
                            System.out.println("---");
                            m.mostrarResumen();
                        }
                        System.out.println("---");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opc != 0);
    }

    private static void inscribirMateria(Estudiante alumno, Scanner sc) {
        System.out.println("\n--- INSCRIPCION ---");
        System.out.print("Nombre de la materia (0 para volver): ");
        String nombre = sc.nextLine();
        if (nombre.equals("0")) {
            return;
        }

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

        System.out.print("Es una materia anual? (s/n): ");
        String esAnual = sc.nextLine();

        int cuatri = 0;
        if (!esAnual.equalsIgnoreCase("s")) {
            System.out.print("Cuatrimestre (1 o 2): ");
            try {
                cuatri = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
            }

            if (cuatri != 1 && cuatri != 2) {
                System.out.println("Error: Cuatrimestre invalido.");
                return;
            }
        }

        System.out.print("Anio: ");
        int anio = 0;
        try {
            anio = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
        }

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
        System.out.print("Ingrese el codigo de la materia a dar de baja (0 para volver): ");
        String codigo = sc.nextLine();
        if (codigo.equals("0")) {
            return;
        }
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
        List<IConsultable> consultables = new ArrayList<>();
        consultables.add(alumno); // El estudiante implementa IConsultable
        for (InscripcionMateria ins : alumno.getMaterias()) {
            consultables.add(ins.getMateria()); // Materia implementa IConsultable
        }

        System.out.println("Iterando Consultables...");
        for (IConsultable c : consultables) {
            System.out.println("-----------------");
            c.mostrarResumen();
        }
        System.out.println("-----------------");

        int regulares = 0, libres = 0;
        for (InscripcionMateria ins : alumno.getMaterias()) {
            if (ins.getCondicion().equals("Regular")) {
                regulares++;
            } else {
                libres++;
            }
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
            for (double n : todasLasNotasAprobadas) {
                suma += n;
            }
            System.out.println("Estadisticas de notas aprobadas -> Max: " + max + " | Min: " + min + " | Promedio: " + (suma / todasLasNotasAprobadas.size()));
        } else {
            System.out.println("No hay materias aprobadas.");
        }
    }

    private static void reporteRanking(Estudiante alumno) {
        System.out.println("\n4. Ranking de Materias (BONUS Interface IRankeable)");
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
            System.out.println((i + 1) + " Puesto - " + ranking.get(i).getMateria().getNombre() + " | Puntaje: " + ranking.get(i).getPuntajeRanking());
        }
    }
}
