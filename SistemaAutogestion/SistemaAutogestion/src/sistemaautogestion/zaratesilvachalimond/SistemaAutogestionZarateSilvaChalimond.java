package sistemaautogestion.zaratesilvachalimond;

import sistemaautogestion.zaratesilvachalimond.vista.AutogestionView;

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
                    // ============================================================
                    //  INTERRUPTOR DE PERSISTENCIA
                    //    true  -> Base de datos MySQL  (BONUS)
                    //    false -> Archivos de texto .txt (OBLIGATORIO)
                    //  Cambiá solo esta línea para elegir cómo se guardan los datos.
                    // ============================================================
                    boolean USAR_BASE_DE_DATOS = true;

                    sistemaautogestion.zaratesilvachalimond.daos.bd.EstudianteDAO estudianteDAO;
                    sistemaautogestion.zaratesilvachalimond.daos.bd.MateriaDAO materiaDAO;
                    sistemaautogestion.zaratesilvachalimond.daos.bd.InscripcionDAO inscripcionDAO;

                    if (USAR_BASE_DE_DATOS) {
                        java.sql.Connection conexion = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/autogestion_estudiantil", "root", "");
                        estudianteDAO  = new sistemaautogestion.zaratesilvachalimond.daos.bd.jdbc.EstudianteDAOMySQL(conexion);
                        materiaDAO     = new sistemaautogestion.zaratesilvachalimond.daos.bd.jdbc.MateriaDAOMySQL(conexion);
                        inscripcionDAO = new sistemaautogestion.zaratesilvachalimond.daos.bd.jdbc.InscripcionDAOMySQL(conexion);
                    } else {
                        estudianteDAO  = new sistemaautogestion.zaratesilvachalimond.daos.archivostxt.EstudianteDAO();
                        materiaDAO     = new sistemaautogestion.zaratesilvachalimond.daos.archivostxt.MateriaDAO();
                        inscripcionDAO = new sistemaautogestion.zaratesilvachalimond.daos.archivostxt.InscripcionMateriaDAO();
                    }

                    sistemaautogestion.zaratesilvachalimond.controlador.AutogestionController controlador = new sistemaautogestion.zaratesilvachalimond.controlador.AutogestionController(estudianteDAO, materiaDAO, inscripcionDAO);

                    new AutogestionView(controlador).setVisible(true);
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Error crítico al iniciar:\n" + e.getMessage());
                    System.exit(1);
                }
            }
        });
    }
}
