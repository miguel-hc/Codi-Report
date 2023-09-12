package Controller;

import System.dataFormat;
import View.boucher;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author MiguelCastellanos
 */
public class BoucherController implements ActionListener {

    boucher vista;
    static BoucherController controller = null;
    dataFormat dateformat;

    public BoucherController(boucher vista) {
        this.vista = vista;
        this.vista.btnvalidar.addActionListener(this);
        this.vista.btnguardar.addActionListener(this);
        dateformat = new dataFormat();
        Calendar c2 = new GregorianCalendar();
        this.vista.jcalendario.setCalendar(c2);
    }

    public static BoucherController getBoucherController(boucher vista) {
        if (controller == null) {
            controller = new BoucherController(vista);
        }
        return controller;
    }

    public double getCajaMenudeo() {
        double total;
        total = (double) this.vista.cmcompra.getValue() + (double) this.vista.cmretiro.getValue();
        return total;
    }

    public double getCajaMayoreo() {
        double total;
        total = (double) this.vista.cmacompras.getValue() + (double) this.vista.cmaretiros.getValue();
        return total;
    }

    public double getCajaAdministrador() {
        double total;
        total = (double) this.vista.cacomptras.getValue() + (double) this.vista.caretiros.getValue();
        return total;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vista.btnvalidar) {
            vista.txtmenudeo.setText(getCajaMenudeo() + "");
            vista.txtmayoreo.setText(getCajaMayoreo() + "");
            vista.txtadministrador.setText(getCajaAdministrador() + "");
        }
        if (e.getSource() == this.vista.btnguardar){
            try {
                ReporteBoucher.getReporteBoucher(vista).getReporteVoucher();
            } catch (IOException ex) {
                Logger.getLogger(BoucherController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JRException ex) {
                Logger.getLogger(BoucherController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(BoucherController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
