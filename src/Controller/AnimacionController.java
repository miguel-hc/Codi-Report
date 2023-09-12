package Controller;

import ConnnecionDB.ConneccionForControl;
import System.ReadParamSystem;
import System.RunView;
import View.Inicio;
import View.InicioAnimacion;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author MiguelCastellanos
 */
public class AnimacionController {

    InicioAnimacion animacion;

    public AnimacionController(InicioAnimacion animacion) throws InterruptedException, SQLException {
        this.animacion = animacion;
        startAnimacion();
    }

    public boolean startAnimacion() throws InterruptedException, SQLException {
        boolean bandera = false;
        ReadParamSystem.getReadParamSystem().readParamSystem();
        ArrayList info = new ArrayList();
        int efect = 0;
        String efect2 = ".";
        info.add("Cargando Archivos");
        info.add("Cargando Configuración");
        info.add("Verificando Conexión");
        int cont = 0;
        for (int i = 0; i < info.size(); i++) {
            Thread.sleep(1 * 1000);
            for (int j = 0; j < 33; j++) {
                Thread.sleep(1 * 10);
                efect = efect + 1;
                this.animacion.progreso.setText(efect + "");
                this.animacion.txtloading.setText(info.get(i).toString() + efect2);
            }
            efect2 = efect2 + ".";
            this.animacion.txtloading.setText(info.get(i).toString() + efect2);
        }
        if (ControlController.getControlController().getConnexion() == null) {
            JOptionPane.showMessageDialog(animacion, "Error de conexion a la bd de Control");
            System.exit(0);
        } else if (PerfilesSaeController.getPerfilesSaeController() == null) {
            JOptionPane.showMessageDialog(animacion, "Error de conexion a la bd de Perfiles");
            System.exit(0);
        } else if (CorteProveedoresController.getCorteProveedoresController().getConnexion() == null) {
            JOptionPane.showMessageDialog(animacion, "Error de conexion a la bd de Aspel Sae");
            System.exit(0);
        } else if (CorteCajasAspelCajaController.getCorteCajasAspelCajaController().getConnexion() == null) {
            JOptionPane.showMessageDialog(animacion, "Error de conexion a la bd de Aspel Caja");
            System.exit(0);
        }
        cont++;
        this.animacion.dispose();
        ReadParamSystem r = new ReadParamSystem();
        r.readParamSystem();
        Inicio inicio = new Inicio();
        RunView views = new RunView();
        InicioCorteSaeController control = new InicioCorteSaeController(inicio, views);
        inicio.setVisible(true);
        return bandera;
    }

}
