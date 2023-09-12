package codi.report.lite;

import Controller.AnimacionController;
import Controller.InicioCorteSaeController;
import System.ReadParamSystem;
import System.RunView;
import View.Inicio;
import View.InicioAnimacion;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

/**
 *
 * @author MiguelCastellanos
 */
public class CodiReportLite {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, InterruptedException {
        int desktopwidth = 0;
        int desktopheigth = 0;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        desktopwidth = screenSize.width;
        desktopheigth = screenSize.height;
        InicioAnimacion animacion = new InicioAnimacion();
        animacion.setVisible(true);
        animacion.setLocation(desktopwidth / 2-237, desktopheigth / 2-165);
        AnimacionController controller = new AnimacionController(animacion);
    }

}
